package com.zwrtc.jni.type;

// 表示原始视频数据的旋转角度，主要用于对原始视频数据进行处理的功能接口中
public enum VideoRotation {
    kVideoRotation0(0),     //< 不旋转
    kVideoRotation90(90),   //< 旋转90度
    kVideoRotation180(180), //< 旋转180度
    kVideoRotation270(270); //< 旋转270度
    private final int value;

    private VideoRotation(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
