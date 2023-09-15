package com.zwrtc.jni.track;

public class Track {
    public long nativeTrackPtr;
    public String id;
    public String kind;
    public String userId;
    public String tag;
    private boolean muted;
    public boolean isAudio;
    public boolean isVideo;
    public boolean isMuted;
    public boolean isRemote;

    void setMuted(boolean muted) {
        this.muted = muted;
    }
}
