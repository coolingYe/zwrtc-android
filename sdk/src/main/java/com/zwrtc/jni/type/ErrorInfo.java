package com.zwrtc.jni.type;

// 错误信息
public class ErrorInfo {
    private int code;       //< 错误码
    private String message; //< 错误描述
    private String reason;  //< 错误原因

    public ErrorInfo(int code, String message, String reason) {
        this.code = code;
        this.message = message;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
