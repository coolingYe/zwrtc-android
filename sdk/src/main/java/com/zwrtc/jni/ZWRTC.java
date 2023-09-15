package com.zwrtc.jni;

import com.zwrtc.jni.type.JoinConfig;
import com.zwrtc.jni.type.LogLevel;

public class ZWRTC {
    static {
        System.loadLibrary("zwrtc");
    }

    public native void initialize();

    public native void setLogLevel(LogLevel logLevel);
    public native JoinConfig makeJoinConfig(String roomId, String serverUrl);
}
