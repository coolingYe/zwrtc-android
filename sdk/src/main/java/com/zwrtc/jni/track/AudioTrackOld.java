package com.zwrtc.jni.track;

public interface AudioTrackOld extends TrackOld {
    interface FrameListener {
        void OnAudioFrame(String user_id, String track_id, byte[] data, int data_size,
                          int bits_per_sample, int sample_rate, int channels);
    }

    void SetAudioFrameListener(FrameListener listener);
    void SetVolume(float volume);
    float GetVolumeLevel();
}
