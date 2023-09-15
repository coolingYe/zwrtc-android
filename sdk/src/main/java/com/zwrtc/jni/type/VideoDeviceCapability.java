package com.zwrtc.jni.type;

// 视频设备能力
public class VideoDeviceCapability {
    public int width;             //< 宽
    public int height;            //< 高
    public int max_fps;           //< 最大帧率
    VideoFrameType video_type;    //< 图像帧格式
    public boolean interlaced;

    public VideoDeviceCapability() {

    }
}
