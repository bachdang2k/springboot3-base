package vivas.common.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vivas.common.AppConf;
import vivas.common.AppException;
import vivas.common.Constant;
import vivas.common.enums.common.JWTEnum;
import vivas.service.UserDetailsImpl;

import java.util.Date;
@Slf4j
@Component
public class JwtUtils {

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()
                        + AppConf.JWT_EXPIRATION_DAY * Constant.milliseconds1day
                        //+ 60000
                ))
                .signWith(SignatureAlgorithm.HS512, AppConf.JWT_SECRET).compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()
                        + AppConf.JWT_EXPIRATION_DAY * Constant.milliseconds1day
                        //+ 60000
                )).signWith(SignatureAlgorithm.HS512, AppConf.JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(AppConf.JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public void validateJwtToken(String authToken) throws AppException {
        try {
            Jwts.parser().setSigningKey(AppConf.JWT_SECRET).parseClaimsJws(authToken);
            //return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new AppException(JWTEnum.ERROR_0.getCode(), JWTEnum.ERROR_0.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AppException(JWTEnum.ERROR_1.getCode(), JWTEnum.ERROR_1.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new AppException(JWTEnum.ERROR_2.getCode(), JWTEnum.ERROR_2.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new AppException(JWTEnum.ERROR_3.getCode(), JWTEnum.ERROR_3.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new AppException(JWTEnum.ERROR_4.getCode(), JWTEnum.ERROR_4.getMessage());
        }
        //return false;
    }
}
