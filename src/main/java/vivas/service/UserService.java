package vivas.service;

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vivas.common.AppException;
import vivas.common.enums.auth.LoginEnum;
import vivas.common.enums.auth.RegisterEnum;
import vivas.controller.request.RegRequest;
import vivas.repo.UserRepository;
import vivas.repo.entity.User;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ////
    public void addUser(RegRequest request) throws AppException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            log.info("Saved new user");
        } else {
            log.info("User {} already exists", request.getUsername());
            throw new AppException(RegisterEnum.ERROR_1.getCode(), RegisterEnum.ERROR_1.getMessage());
        }
    }

    public void exists(String username) throws AppException {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isPresent())
            throw new AppException(LoginEnum.ERROR_4.getCode(), LoginEnum.ERROR_4.getMessage());
        else throw new AppException(LoginEnum.ERROR_3.getCode(), LoginEnum.ERROR_3.getMessage());
    }
}
