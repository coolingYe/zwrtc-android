package com.zwrtc.jni.type;

public class Property {
    public enum Protocol {
        kProtoo,
        kZWAI,
    }

    public Protocol protocol = Protocol.kZWAI;
    public boolean groupPhoto = false;
    public String caFile;
}
