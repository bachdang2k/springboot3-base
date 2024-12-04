package vivas.security.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {
    private String userId;
    private String name;
    private String email;
}
