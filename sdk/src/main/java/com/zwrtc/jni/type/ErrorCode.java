package com.zwrtc.jni.type;

// brief 错误码类型
public enum ErrorCode {
    None(0),                 //< 无
    NotConnected(1),         //< 未连接/未加入房间
    Cancel(2),               //< 操作被取消
    NetworkError(3),         //< 网络错误
    NetworkTimeout(4),       //< 网络超时
    DeviceNotFound(5),       //< 找不到设备
    RequestProduceFailed(6), //< 无法生产媒体
    AlreadyProduce(7),       //< 已经生产了媒体, 无法再次生产
    NotSupported(8);         //< 不支持的操作

    private final int value;

    private ErrorCode(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
