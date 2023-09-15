package com.zwrtc.jni.type;

public enum LogLevel {
    kTrace(0),
    kDebug(1),
    kInfo(2),
    kWarn(3),
    kError(4),
    kCritical(5),
    kOff(6);

    private final int value;

    private LogLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
