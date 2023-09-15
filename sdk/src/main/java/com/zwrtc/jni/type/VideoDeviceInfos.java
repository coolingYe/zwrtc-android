package com.zwrtc.jni.type;

import java.util.List;

// 视频设备信息
public class VideoDeviceInfos {
    public int index;
    public String name;
    public String uniqueId;
    public List<VideoDeviceCapability> capabilities;

    public VideoDeviceInfos() {

    }
}
