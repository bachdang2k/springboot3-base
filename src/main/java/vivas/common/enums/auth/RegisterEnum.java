package vivas.common.enums.auth;

public enum RegisterEnum {
    ERROR_1(1, "Người dùng đã tồn tại");
    private Integer code;
    private String message;

    RegisterEnum(Integer code, String message) {
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
