package vivas.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vivas.common.enums.common.JWTEnum;
import vivas.common.enums.common.SysEnum;
import vivas.common.util.url.UrlPattern;
import vivas.service.CommonService;
import vivas.service.dto.PmsDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private CommonService commonService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization=Bearer <token>
        String reqUrl = request.getRequestURI().substring(request.getContextPath().length());
        log.info("request={}, path={}", request.getRequestURI(), reqUrl);
        if (reqUrl.startsWith("/swagger-ui/")) {
            filterChain.doFilter(request, response);
            return;
        } else if (reqUrl.startsWith("/api/app")) {

        } else if (reqUrl.startsWith("/api/web")) {
            if (!(reqUrl.startsWith("/api/web/auth/register")
                    || reqUrl.startsWith("/api/web/auth/login")
                    || reqUrl.startsWith("/api/web/auth/validate_token")
                    || reqUrl.startsWith("/api/web/auth/refreshtoken")
            )
            ) {
                String requestHeader = request.getHeader("Authorization");
                log.info("Header:  {}", requestHeader);
                String username = null;
                String token = null;
                if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                    try {
                        token = requestHeader.substring(7);
                        username = this.jwtHelper.getUsernameFromToken(token);
                    } catch (IllegalArgumentException e) {
                        log.error("{}", JWTEnum.ERROR_9.getMessage(), e);
                        res(response, HttpStatus.OK, JWTEnum.ERROR_9.getCode(), JWTEnum.ERROR_9.getMessage());
                        return;
                    } catch (ExpiredJwtException e) {
                        log.error("{}", JWTEnum.ERROR_2.getMessage());
                        res(response, HttpStatus.OK, JWTEnum.ERROR_2.getCode(), JWTEnum.ERROR_2.getMessage());
                        return;
                    } catch (MalformedJwtException e) {
                        log.error("{}", JWTEnum.ERROR_8.getMessage());
                        res(response, HttpStatus.OK, JWTEnum.ERROR_8.getCode(), JWTEnum.ERROR_8.getMessage());
                        return;
                    } catch (StringIndexOutOfBoundsException e) {
                        log.error("{}", JWTEnum.ERROR_7.getMessage());
                        res(response, HttpStatus.OK, JWTEnum.ERROR_7.getCode(), JWTEnum.ERROR_7.getMessage());
                        return;
                    } catch (Exception e) {
                        log.error("Loi xay ra", e);
                        res(response, HttpStatus.OK, SysEnum.ERROR__1.getCode(), SysEnum.ERROR__1.getMessage());
                        return;
                    }
                } else {
                    log.info("Invalid Header Value!!");
                    res(response, HttpStatus.OK, JWTEnum.ERROR_7.getCode(), JWTEnum.ERROR_7.getMessage());
                    return;
                }
                log.info("username={}", username);
                //
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //fetch user detail from username
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
                    if (validateToken) {
                        //===== Check permisssion
                        String path = reqUrl;
                        String method = request.getMethod().toLowerCase();
                        log.info("check permission [{}, {}]", method, path);
                        List<PmsDto> lst = commonService.obtainPms(username);
                        if (!ObjectUtils.isEmpty(lst)) {
                            Integer size = 0;
                            if (!ObjectUtils.isEmpty(lst))
                                size = lst.size();
                            log.info("found lst size={}", size);
                            Boolean check = false;
                            //
                            check = lst.stream().anyMatch(o1 -> {
                                try {
                                    UrlPattern urlPattern = new UrlPattern(o1.getPath());
                                    if (urlPattern.matches(path) && method.equals(o1.getMethod()))
                                        return true;
                                    else
                                        return false;
                                } catch (Exception e) {
                                    return false;
                                }
                            });
                            if (check) {
                                //set the authentication
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                log.info("xac thuc ok [{}]", username);
                            } else {
                                log.error("{}", SysEnum.ACCESS_DENIED.getMessage());
                                res(response, HttpStatus.OK, SysEnum.ACCESS_DENIED.getCode(), SysEnum.ACCESS_DENIED.getMessage());
                                return;
                            }
                        } else {
                            log.error("[1]{}", SysEnum.ACCESS_DENIED.getMessage());
                            res(response, HttpStatus.OK, SysEnum.ACCESS_DENIED.getCode(), SysEnum.ACCESS_DENIED.getMessage());
                            return;
                        }
                    } else {
                        log.error("{}", JWTEnum.ERROR_1.getMessage());
                        res(response, HttpStatus.OK, JWTEnum.ERROR_1.getCode(), JWTEnum.ERROR_1.getMessage());
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void res(HttpServletResponse response, HttpStatus httpStatus, int code, String message) throws IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("code", code);
        errorDetails.put("message", message);
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getWriter(), errorDetails);
    }
}
