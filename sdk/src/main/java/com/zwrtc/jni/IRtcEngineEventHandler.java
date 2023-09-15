package com.zwrtc.jni;

import static com.zwrtc.jni.utils.YUVUtils.convertNV21BufferToVideoFrame;

import com.zwrtc.jni.track.Track;
import com.zwrtc.jni.type.ConnectionClosedInfo;
import com.zwrtc.jni.type.Message;
import com.zwrtc.jni.type.RoomState;
import com.zwrtc.jni.type.VideoFrame;

public abstract class IRtcEngineEventHandler implements IRtcEventHolder {
    public IRtcEngineEventHandler() {

    }

    public void onRoomStateChanged(RoomState state, ConnectionClosedInfo closedInfo) {

    }

    public void onUserJoined(String remoteUserId, String displayName, String userData) {

    }

    public void onUserLeft(String remoteUserId) {

    }

    public void onMessageReceived(Message message) {

    }

    public void onTrackPaused(Track track) {

    }

    public void onTrackResumed(Track track) {

    }

    public void onLocalTrackCreate(Track track) {

    }

    public void onLocalTrackRemove(Track track) {

    }

    public void onRemoteTrackCreate(Track track) {

    }

    public void onRemoteTrackRemove(Track track) {

    }

    public void onVideoFrame(String user_id, String track_id, VideoFrame video_frame) {
        onVideoFrame(user_id, track_id, convertNV21BufferToVideoFrame(video_frame));
    }

    public void onVideoFrame(String user_id, String track_id, org.webrtc.VideoFrame video_frame) {

    }
}
