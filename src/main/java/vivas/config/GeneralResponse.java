package vivas.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
