package com.zwrtc.jni.track;

import com.zwrtc.jni.type.VideoFrame;

public interface VideoTrackOld extends TrackOld {
    interface FrameListener {
        void OnVideoFrame(String user_id, String track_id, VideoFrame video_frame);
    }

    void SetVideoFrameListener(FrameListener listener);
}
