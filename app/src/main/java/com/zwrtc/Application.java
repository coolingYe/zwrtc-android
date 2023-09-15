package com.zwrtc;

import org.webrtc.ContextUtils;
import org.webrtc.PeerConnectionFactory;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.initialize(getApplicationContext());
        PeerConnectionFactory.initialize(
                PeerConnectionFactory.InitializationOptions
                        .builder(getApplicationContext())
                        .setEnableInternalTracer(true)
                        .createInitializationOptions()
        );
    }
}
