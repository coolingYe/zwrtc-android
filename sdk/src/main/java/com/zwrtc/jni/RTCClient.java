package com.zwrtc.jni;

import com.zwrtc.jni.type.RTCClientConfig;

public abstract class RTCClient {

    protected static RTCClientImpl mInstance = null;

    public RTCClient() {

    }

    public static synchronized RTCClient create(RTCClientConfig config) {
        if (config != null) {
            if (mInstance == null) {
                mInstance = new RTCClientImpl(config);
            }
            return mInstance;
        }
        return null;
    }

    public abstract void joinChannel(RTCClientConfig config);

    public abstract void leaveChannel();

    public abstract void initPublishVideo(int w, int h, int fps, int bitrate);

    public abstract void pushVideoTrack(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timesTampUs);

    public abstract void setOnVideoFrameListener(long trackAdr);

}
