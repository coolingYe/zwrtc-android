package com.zwrtc.jni;

import static com.zwrtc.jni.Constants.SERVICE_URL;

import android.content.Context;

import com.zwrtc.jni.type.RTCClientConfig;

public abstract class RTCClient {
    static {
        System.loadLibrary("zwrtc");
    }

    protected static RTCClientImpl mInstance = null;

    public RTCClient() {

    }

    public static synchronized RTCClient create(Context context, String roomId, IRtcEngineEventHandler rtcEngineEventHandler) throws Exception {
        if (context != null) {
            RTCClientConfig config = RTCClientConfig.makeJoinConfig(roomId, SERVICE_URL);
            if (mInstance == null) {
                mInstance = new RTCClientImpl(config);
            }
            return mInstance;
        }
        return null;
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

    public abstract void setOnVideoFrameListener(long trackAdr);

}
