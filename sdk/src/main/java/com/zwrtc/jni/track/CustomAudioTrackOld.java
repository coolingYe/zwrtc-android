package com.zwrtc.jni.track;

public interface CustomAudioTrackOld extends AudioTrackOld {
    int PushAudioFrame(byte[] data, int data_size, int bits_per_sample,
                       int sample_rate, int channels);
}
