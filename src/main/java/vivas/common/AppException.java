package vivas.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends Exception {
    private Integer code;
    private String message;
}
