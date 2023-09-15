package com.zwrtc;

import com.zwrtc.jni.RTCFactory;

import org.webrtc.ContextUtils;
import org.webrtc.PeerConnectionFactory;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RTCFactory.initialize(getApplicationContext());
    }
}
