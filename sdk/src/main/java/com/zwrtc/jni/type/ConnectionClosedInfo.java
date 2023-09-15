package com.zwrtc.jni.type;

// 断开连接时状态信息
public class ConnectionClosedInfo {
    public enum Reason {
        kLeave,         //< 主动退出
        kKickOut,       //< 被服务器踢出房间 / 服务器关闭连接
        kRoomClosed,    //< 房间被关闭
        kRoomFull,      //< 房间人数已满
        kRoomError,     //< 异常断开
        kNone           //< 无
    }

    public Reason reason = Reason.kNone;
    public int errorCode = 0;      //< Reserved field(保留字段)
    public String errorMessage;    //< Reserved field(保留字段)

    public ConnectionClosedInfo(Reason reason) {
        this.reason = reason;
    }

    public ConnectionClosedInfo(Reason reason, int code, String message) {
        this.reason = reason;
        this.errorCode = code;
        this.errorMessage = message;
    }
}
