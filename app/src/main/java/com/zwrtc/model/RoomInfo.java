package com.zwrtc.model;

public class RoomInfo {
    private String roomId;
    private String displayName;
    private boolean cameraEnable;
    private boolean microphoneEnable;

    public RoomInfo(String roomId, String displayName, boolean cameraEnable, boolean microphoneEnable) {
        this.roomId = roomId;
        this.displayName = displayName;
        this.cameraEnable = cameraEnable;
        this.microphoneEnable = microphoneEnable;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isCameraEnable() {
        return cameraEnable;
    }

    public void setCameraEnable(boolean cameraEnable) {
        this.cameraEnable = cameraEnable;
    }

    public boolean isMicrophoneEnable() {
        return microphoneEnable;
    }

    public void setMicrophoneEnable(boolean microphoneEnable) {
        this.microphoneEnable = microphoneEnable;
    }
}
