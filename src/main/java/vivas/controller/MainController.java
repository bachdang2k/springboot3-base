package vivas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import vivas.service.UserService;

@RestController
//@RequestMapping("/api/v1/home")
public class MainController {
    @Autowired
    private UserService userService;

    // localhost:8081/home/user
    /*
    @GetMapping("/current-user")
    public ResponseEntity getLoggedInUser(Principal principal) {
        Integer code = 0;
        String message = "Success";
        String data = principal.getName();
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).data(data).build());
    }
    */
}
