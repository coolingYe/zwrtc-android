package com.zwrtc.jni;

import android.content.Context;

import org.webrtc.PeerConnectionFactory;

public class RTCFactory {

    static {
        System.loadLibrary("zwrtc");
    }

    /**
     * Initializes libwebrtc.
     *
     * @param appContext app context
     */
    public static void initialize(Context appContext) {
        initialize(appContext, null);
        initialize();
    }

    /**
     * Initializes libwebrtc.
     *
     * @param appContext app context
     * @param fieldTrials fieldTrials desc
     */
    public static void initialize(Context appContext, String fieldTrials) {
        PeerConnectionFactory.InitializationOptions options =
                PeerConnectionFactory.InitializationOptions.builder(appContext)
                        .setFieldTrials(fieldTrials)
                        .setEnableInternalTracer(true)
                        .setNativeLibraryName("libjingle_peerconnection_so")
                        .createInitializationOptions();
        PeerConnectionFactory.initialize(options);
    }

    public static native void initialize();
}
