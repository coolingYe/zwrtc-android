package com.zwrtc.jni.type;

public class Message {
    public String label;
    public String msgId;       // 消息唯一 ID
    public String msgSenderId; // 消息发送者的 user ID
    public String msgText;     // 消息内容
    public long timestamp;     // 消息时间戳

    public Message() {

    }
}
