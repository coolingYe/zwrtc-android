package com.zwrtc.jni.type;

public enum RoomState {
    UNKNOWN(0),      //< 未知状态
    CONNECTING(1),   //< 连接中
    CONNECTED(2),    //< 已连接
    RECONNECTING(3), //< 重新连接中
    CLOSED(4);       //< 已关闭

    private final int value;

    private RoomState(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
