package com.zwrtc.jni.type;

// 视频数据格式
public enum VideoFrameType {
    kUnknown(0),
    kI420(1),
    kIYUV(2),
    kRGB24(3),
    kARGB(4),
    kRGB565(5),
    kYUY2(6),
    kYV12(7),
    kUYVY(8),
    kMJPEG(9),
    kBGRA(10);

    private final int value;

    private VideoFrameType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
