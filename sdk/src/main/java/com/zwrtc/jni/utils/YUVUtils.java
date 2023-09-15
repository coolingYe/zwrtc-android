package com.zwrtc.jni.utils;

import org.webrtc.NV21Buffer;
import org.webrtc.VideoFrame;

public class YUVUtils {

    public static byte[] mergeYUV420(byte[] yData, byte[] uData, byte[] vData, int width, int height) {
        int ySize = width * height;
        int uvSize = ySize / 4;
        byte[] mergedData = new byte[ySize + 2 * uvSize];

        System.arraycopy(yData, 0, mergedData, 0, ySize); // 复制 Y 分量数据

        int uIndex = 0;
        int vIndex = 0;
        for (int i = ySize; i < mergedData.length; i += 2) {
            mergedData[i] = uData[uIndex]; // 复制 U 分量数据
            mergedData[i + 1] = vData[vIndex]; // 复制 V 分量数据
            uIndex++;
            vIndex++;
        }

        return mergedData;
    }

    public static VideoFrame convertNV21BufferToVideoFrame(com.zwrtc.jni.type.VideoFrame videoFrame) {
        NV21Buffer nv21Buffer = new NV21Buffer(mergeYUV420(videoFrame.yData, videoFrame.uData, videoFrame.vData, videoFrame.width, videoFrame.height), videoFrame.width, videoFrame.height, null);
        return new VideoFrame(nv21Buffer, 0, videoFrame.timestamp * 1000);
    }
}
