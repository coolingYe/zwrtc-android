package com.zwrtc.jni.track;

public interface CameraVideoTrackOld extends VideoTrackOld {
    void StartCapture();
    void StopCapture();
    void SetMirror(boolean mirror);
}
