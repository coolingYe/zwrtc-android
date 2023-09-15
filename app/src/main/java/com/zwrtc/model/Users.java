package com.zwrtc.model;

import java.util.Set;

public class Users {
    String uid;
    String displayName;
    Set<String> tracks;

    public Users(String uid, String displayName, Set<String> consumers) {
        this.uid = uid;
        this.displayName = displayName;
        this.tracks = consumers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<String> getTracks() {
        return tracks;
    }

    public void setTracks(Set<String> tracks) {
        this.tracks = tracks;
    }
}
