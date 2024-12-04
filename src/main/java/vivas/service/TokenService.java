package vivas.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import vivas.common.AppConf;
import vivas.common.AppException;
import vivas.common.Constant;
import vivas.common.enums.common.JWTEnum;
import vivas.common.jwt.JwtUtils;
import vivas.repo.RefreshTokenRepository;
import vivas.repo.UserRepository;
import vivas.repo.entity.RefreshToken;
import vivas.repo.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TokenService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsImplService userDetailsService;

    //////
    public void validateToken(HttpServletRequest request, String jwt) throws AppException {
        jwtUtils.validateJwtToken(jwt);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        //
        Optional<RefreshToken> opt = refreshTokenRepository.findByUsername(username);
        if (opt.isPresent()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new AppException(JWTEnum.ERROR_5.getCode(), JWTEnum.ERROR_5.getMessage());
        }
    }

    public String executeRefresh(String token) throws AppException {
        Optional<RefreshToken> opt = refreshTokenRepository.findByToken(token);
        if (opt.isPresent()) {
            RefreshToken t = opt.get();
            verifyExpiration(t);
            return jwtUtils.generateTokenFromUsername(t.getUser().getUsername());
        } else {
            throw new AppException(JWTEnum.ERROR_5.getCode(), JWTEnum.ERROR_5.getMessage());
        }
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userRepository.findById(userId).get();
        refreshToken.setUser(user);
        refreshToken.setUsername(user.getUsername());
        //Instant instant = Instant.now();
        //instant = instant.plus(AppConf.JWT_EXPIRATION_DAY, ChronoUnit.DAYS);
        //log.info("//{}//{}", instant, AppConf.JWT_EXPIRATION_DAY);
        refreshToken.setExpiryDate(Instant.now().plus(AppConf.JWT_EXPIRATION_DAY, ChronoUnit.DAYS));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.deleteByUsername(user.getUsername());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) throws AppException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new AppException(JWTEnum.ERROR_6.getCode(), JWTEnum.ERROR_6.getMessage());
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
