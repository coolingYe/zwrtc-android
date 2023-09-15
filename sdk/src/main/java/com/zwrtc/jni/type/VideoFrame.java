package com.zwrtc.jni.type;

import java.util.ArrayList;

// 视频帧描述信息
public class VideoFrame {
    public VideoFrameType type = VideoFrameType.kUnknown;          //< 视频帧像素格式, 一般为 kI420
    public int width = 0;                                          //< 帧宽
    public int height = 0;                                         //< 帧高
    //public byte[][] Data;                                        //< 数据指针, 如 I420 帧中, 前3位元素分别存放 'Y' 'U' 'V' 的首地址
    public byte[] yData;
    public byte[] uData;
    public byte[] vData;
    //public int[] Stride;                                         //< 跨距,  如 I420 帧中, 前3位元素分别存放 'Y' 'U' 'V' 的跨距
    public int yStride;
    public int uStride;
    public int vStride;
    public long timestamp = 0;                                     //< 时间戳
    public VideoRotation rotation = VideoRotation.kVideoRotation0; //< 旋转角

    public VideoFrame() {

    }
}
