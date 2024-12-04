package vivas.common.enums.common;

public enum SysEnum {
    ERROR__2(-2, "Loi xay ra {}"),
    ERROR__1(-1, "Loi he thong"),
    SUCCESS(0, "Success"),
    ACCESS_DENIED(300, "Bạn không có quyền truy cập tính năng này");
    private Integer code;
    private String message;

    SysEnum(Integer code, String message) {
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
