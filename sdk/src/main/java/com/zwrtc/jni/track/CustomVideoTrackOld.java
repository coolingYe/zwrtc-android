package com.zwrtc.jni.track;

public interface CustomVideoTrackOld extends VideoTrackOld {
    void PushVideoFrame(int width, int height, byte[] data_y, int stride_y,
                        byte[] data_u, int stride_u, byte[] data_v,
                        int stride_v, long timestamp_us);
}
