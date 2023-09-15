package com.zwrtc.jni.type;

// 用户参与云合影视频融合的参数
public class FusionUserConfig {
    public int userNumber = 1;         //< 参与合照人数
    public double scale = 1.f;         //< 缩放比例
    public double fromBottomRatio = 0; //
    public double scaleFromLeft = 0;   //< 人像到相机画面中左边距离的系数，默认0.25，范围0.0-1.0(可设置小数点后三位)
    public double scaleFromWidth = 0;  //< 人像区域宽度在相机画面中占比系数，默认0.5，范围：0.0-1.0(可设置小数点后3位)
    public int rotationAngle = 0;      //< 人像在背景合照中的旋转角度，默认0，范围：-90 ~ 90，负数表示逆时针旋转，正数表示顺时针旋转

    public FusionUserConfig() {

    }
}
