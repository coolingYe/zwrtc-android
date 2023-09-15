package com.zwrtc.jni.type;

// 大小流对应配置
public enum TrackProfile {
    kLow(0),
    kMedium(1),
    kHigh(2);

    private final int value;

    private TrackProfile(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
