package vivas.common.enums.auth;

public enum LoginEnum {
    ERROR_1(1, "Tên tài khoản không được để trống"),
    ERROR_2(2, "Mật khẩu không được để trống"),
    ERROR_3(3, "Tên đăng nhập không tồn tại"),
    ERROR_4(4, "Mật khẩu không đúng");
    private Integer code;
    private String message;

    LoginEnum(Integer code, String message) {
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
