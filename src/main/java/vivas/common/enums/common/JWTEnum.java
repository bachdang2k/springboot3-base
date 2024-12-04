package vivas.common.enums.common;

public enum JWTEnum {
    ERROR_0(100, "Invalid JWT signature"),
    ERROR_1(101, "Invalid JWT token"),
    ERROR_2(102, "JWT token is expired"),
    ERROR_3(103, "JWT token is unsupported"),
    ERROR_4(104, "JWT claims string is empty"),
    ERROR_5(105, "Refresh token is not in database"),
    ERROR_6(106, "Refresh token is expired"),
    ERROR_7(107, "Token không hợp lệ"),
    ERROR_8(108, "Token không đúng định dạng"),
    ERROR_9(109, "IllegalArgumentException");
    private Integer code;
    private String message;

    JWTEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
