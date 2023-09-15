package com.zwrtc.jni.type;

// 创建云合影房间所需的配置参数
public class CreateFusionRoomConfig {
    public String backGroundUrl;                 //< 背景图片url地址

    public int frameHeight = 1080;               //< 云合影房间视频流分辨率高
    public int frameRate = 15;                   //< 云合影房间视频流帧率
    public int frameWidth = 1920;                //< 云合影房间视频流分辨率宽

    public String fusionType = "StandardFusion"; //< 融合类型, 一般使用默认值即可
    public String rtcType = "ZWNRTC2";           //< rtc类型, 一般使用默认值即可
    public VideoType videoType = VideoType.RTC_VIDEO_FUSION_CALL;

    public enum VideoType {
        RTC_VIDEO_CALL(1),
        RTC_VIDEO_FUSION_CALL(2);

        private int value;

        VideoType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
