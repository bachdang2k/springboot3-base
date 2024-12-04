package vivas.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vivas.common.AppException;
import vivas.common.AppHelper;
import vivas.common.enums.auth.LoginEnum;
import vivas.common.enums.common.SysEnum;
import vivas.config.GeneralResponse;
import vivas.controller.request.RegRequest;
import vivas.controller.request.TokenRefreshRequest;
import vivas.controller.response.LoginResponse;
import vivas.repo.entity.RefreshToken;
import vivas.common.jwt.JWTHelper;
import vivas.security.model.LoginRequest;
import vivas.service.TokenService;
import vivas.service.UserDetailsImpl;
import vivas.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/web/auth")
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTHelper helper;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        Integer code = 0;
        String message = "Success";
        LoginResponse loginResponse = null;
        try {
            validateLogin(request);
            this.doAuthenticate(request.getUsername(), request.getPassword());
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getUsername());
            RefreshToken refreshToken = tokenService.createRefreshToken(userDetails.getId());
            String token = this.helper.generateToken(userDetails);
            loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setRefreshtoken(refreshToken.getToken());
        } catch (Exception ex) {
            Throwable rootcause = AppHelper.getrootcause(ex);
            if (rootcause instanceof AppException) {
                AppException apex = (AppException) rootcause;
                code = apex.getCode();
                message = apex.getMessage();
            } else {
                if (ex instanceof BadCredentialsException) {
                    try {
                        userService.exists(request.getUsername());
                    } catch (Exception ex1) {
                        if (ex1 instanceof AppException) {
                            AppException apex1 = (AppException) ex1;
                            code = apex1.getCode();
                            message = apex1.getMessage();
                        } else {
                            log.error("[0] Loi he thong", ex1);
                            code = SysEnum.ERROR__1.getCode();
                            message = SysEnum.ERROR__1.getMessage();
                        }
                    }
                } else {
                    log.error("[1] Loi he thong", ex);
                    code = SysEnum.ERROR__1.getCode();
                    message = SysEnum.ERROR__1.getMessage();
                }
            }
        }
        log.info("// finished login");
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).data(loginResponse).build());
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        log.info("refreshtoken => {}", request.getRefreshtoken());
        Integer code = SysEnum.SUCCESS.getCode();
        String message = SysEnum.SUCCESS.getMessage();
        String data = null;
        //////
        String requestRefreshToken = request.getRefreshtoken();
        try {
            data = tokenService.executeRefresh(requestRefreshToken);
        } catch (Exception ex) {
            Throwable rootcause = AppHelper.getrootcause(ex);
            if (rootcause instanceof AppException) {
                AppException apex = (AppException) rootcause;
                code = apex.getCode();
                message = apex.getMessage();
            } else {
                log.error("Loi xay ra -> {}", ex.getMessage(), ex);
                code = SysEnum.ERROR__1.getCode();
                message = SysEnum.ERROR__1.getMessage();
            }
        }
        log.info("// finished refreshtoken");
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).data(data).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        int code = SysEnum.SUCCESS.getCode();
        String message = SysEnum.SUCCESS.getMessage();
        try {
            UserDetailsImpl userDetails =
                    (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();
            tokenService.deleteByUserId(userId);
        } catch (Exception ex) {
            log.error("Loi xay ra -> {}", ex.getMessage(), ex);
            Throwable rootcause = AppHelper.getrootcause(ex);
            if (rootcause instanceof AppException) {
                AppException apex = (AppException) rootcause;
                code = apex.getCode();
                message = apex.getMessage();
            } else {
                code = SysEnum.ERROR__1.getCode();
                message = SysEnum.ERROR__1.getMessage();
            }
        }
        log.info("// finish logout");
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).build());
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        manager.authenticate(authentication);
    }

    /*
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    */
    ////
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegRequest request) throws Exception {
        int code = SysEnum.SUCCESS.getCode();
        String message = SysEnum.SUCCESS.getMessage();
        try {
            validateRegister(request);
            userService.addUser(request);
        } catch (Exception ex) {
            Throwable rootcause = AppHelper.getrootcause(ex);
            if (rootcause instanceof AppException) {
                AppException apex = (AppException) rootcause;
                code = apex.getCode();
                message = apex.getMessage();
            } else {
                log.error("Loi xay ra -> {}", ex.getMessage(), ex);
                code = SysEnum.ERROR__1.getCode();
                message = SysEnum.ERROR__1.getMessage();
            }
        }
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).build());
    }

    //
    private void validateLogin(LoginRequest req) throws AppException {
        if (StringUtils.isBlank(req.getUsername())) {
            throw new AppException(LoginEnum.ERROR_1.getCode(), LoginEnum.ERROR_1.getMessage());
        }
        if (StringUtils.isBlank(req.getPassword())) {
            throw new AppException(LoginEnum.ERROR_2.getCode(), LoginEnum.ERROR_2.getMessage());
        }
    }

    private void validateRegister(RegRequest jwtReq) throws AppException {
        if (ObjectUtils.isEmpty(jwtReq.getUsername()))
            throw new AppException(LoginEnum.ERROR_1.getCode(), LoginEnum.ERROR_2.getMessage());
        if (ObjectUtils.isEmpty(jwtReq.getPassword()))
            throw new AppException(LoginEnum.ERROR_2.getCode(), LoginEnum.ERROR_2.getMessage());
    }
}
