package com.zwrtc.jni.type;

import android.content.Context;

import com.zwrtc.jni.IRtcEngineEventHandler;

public class RTCClientConfig {
    public Context mContext = null;
    public IRtcEngineEventHandler mRtcEngineEventHandler = null;
    public int mChannelProfile = 0;
    public String mServerUrl;      //< 服务器url(require)
    public String mRoomId;         //< 房间id
    public String mUserId;         //< 用户id
    public String mUserToken;      //< 用户授权token
    public String mAppId;          //< 用户授权appid
    public String mDisplayName;    // optional
    public String mAppData;        // optional


    public RTCClientConfig() {
    }

    public Context getContext() {
        return mContext;
    }

    public IRtcEngineEventHandler getRtcEngineEventHandler() {
        return mRtcEngineEventHandler;
    }

    public String getServerUrl() {
        return mServerUrl;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUserToken() {
        return mUserToken;
    }

    public String getAppId() {
        return mAppId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getAppData() {
        return mAppData;
    }

    public int getChannelProfile() {
        return mChannelProfile;
    }

    public static native RTCClientConfig makeJoinConfig(String roomId, String serverUrl);
}
