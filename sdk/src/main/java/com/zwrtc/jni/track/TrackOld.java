package com.zwrtc.jni.track;

/// 视频/音频媒体轨道的基类; 描述基本的多媒体轨道信息
///  - 当 IsAudio() 为 true 时, 可转换为 AudioTrack 指针
///  - 当 IsVideo() 为 true 时, 可转换为 VideoTrack 指针
public interface TrackOld {
    String getId();
    String getKind();
    String getUserId();
    String getTag();
    boolean isAudio();
    boolean isVideo();
    boolean isMuted();
    void setMuted(boolean muted);
    boolean isRemote();
}
