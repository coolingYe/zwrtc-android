package com.zwrtc.jni;

import com.zwrtc.jni.track.Track;
import com.zwrtc.jni.type.ConnectionClosedInfo;
import com.zwrtc.jni.type.Message;
import com.zwrtc.jni.type.RoomState;
import com.zwrtc.jni.type.VideoFrame;

public interface IRtcEventHolder {
    void onRoomStateChanged(RoomState state, ConnectionClosedInfo closedInfo);

    void onUserJoined(String remoteUserId, String displayName, String userData);

    void onUserLeft(String remoteUserId);

    void onMessageReceived(Message message);

    void onTrackPaused(Track track);

    void onTrackResumed(Track track);

    void onLocalTrackCreate(Track track);

    void onLocalTrackRemove(Track track);

    void onRemoteTrackCreate(Track track);

    void onRemoteTrackRemove(Track track);

    void onVideoFrame(String user_id, String track_id, VideoFrame video_frame);

    void onVideoFrame(String user_id, String track_id, org.webrtc.VideoFrame video_frame);
}
