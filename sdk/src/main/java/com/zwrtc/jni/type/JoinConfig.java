package com.zwrtc.jni.type;

// JoinConfig
public class JoinConfig {
    private String serverUrl;      //< 服务器url(require)
    private String roomId;         //< 房间id
    private String userId;         //< 用户id
    private String userToken;      //< 用户授权token
    private String appId;          //< 用户授权appid
    private String displayName;    // optional
    private String appData;        // optional

    public JoinConfig(String serverUrl, String roomId, String userId, String userToken, String appId, String displayName, String appData) {
        this.serverUrl = serverUrl;
        this.roomId = roomId;
        this.userId = userId;
        this.userToken = userToken;
        this.appId = appId;
        this.displayName = displayName;
        this.appData = appData;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }
}
