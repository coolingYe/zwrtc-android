package com.zwrtc;

import static com.zwrtc.jni.Constants.ROOM_ID;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zwrtc.model.RoomInfo;

import org.webrtc.VideoTrack;

public class RoomStore extends ViewModel {

    public MutableLiveData<VideoTrack> targetTrack = new MutableLiveData<>();

    public MutableLiveData<com.zwrtc.jni.type.VideoFrame> remoteVideoTrack = new MutableLiveData<>();

    public RoomInfo roomInfo = new RoomInfo(ROOM_ID, "DIO", true, true);
}
