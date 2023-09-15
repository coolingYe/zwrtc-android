package com.zwrtc.jni;

import com.zwrtc.jni.track.Track;
import com.zwrtc.jni.type.CreateFusionRoomConfig;
import com.zwrtc.jni.type.FusionUserConfig;
import com.zwrtc.jni.type.JoinConfig;
import com.zwrtc.jni.type.Property;

public class RTCClient {
    static {
        System.loadLibrary("zwrtc");
    }

    /**
     * \brief 创建音视频通话客户端
     */
    public RTCClient() {

    }
    public native void createNativeObject(IRtcEngineEventHandler rtcEngineEventHandler);
    public native void setProperties(Property properties);

    /**
     * \brief 加入普通音视频房间
     *
     * \param config [JoinConfig]
     * \returns void
     */
    public native void join(JoinConfig config);

    /**
     * \brief 离开房间, 适用于普通音视频模式和云合影模式
     *
     * \param 无
     * \returns void
     */
    public native void leave();

    /**
     * \brief 当有远端用户发布媒体流时，自动订阅
     */
    public native void autoSubscribe(boolean video, boolean audio);

    /**
     * \brief 加入云合影音视频房间;
     *  - 房间不存在时创建房间并加入;
     *  - 房间已存在时, 直接加入, 此时 fusionConfig 参数不生效
     *
     * \param config 基本房间信息
     * \param fusionConfig 云合影房间信息
     */
    public native void joinFusionRoom(JoinConfig config, CreateFusionRoomConfig fusionConfig);

    /**
     * \brief 开启云合影模式; 发布视频媒体时开启融合
     */
    public native void startFusion();

    /**
     * \brief 用在取消视频媒体发布时退出融合
     */
    public native void userExitFusion();

    /**
     * \brief 关闭房间时停止融合
     */
    public native void stopFusion();

    /**
     * \brief 更新用户融合合影信息
     *
     * \param fusion_cfg 用户融合信息
     */
    public native void changeFusionUserConfig(FusionUserConfig fusionConfig);

    /**
     * \brief 更换云合影房间的背景
     *
     * \param image_url 背景图片URL地址
     */
    public native void changeFusionBackground(String imageUrl);

    /**
     * \brief 根据索引打开摄像头并发布视频流
     *
     * \param width_cap 宽
     * \param height_cap 高
     * \param fps_cap 帧率
     * \param index 摄像头索引
     * \param error_cb 错误回调
     */
    public native void publishCameraWithIndex(int widthCap, int heightCap, int fpsCap, int index);

    /**
     * \brief 取消发布摄像头视频流
     */
    public native void unPublishCamera();

    /**
     * \brief 发布自定义视频输入的视频流
     *
     * \param w 宽
     * \param h 高
     * \param fps 帧率
     * \param bitrate 码率
     * \param result_cb 发布结果回调, 请处理可能出现的错误,
     *                 并通过 CustomVideoTrack::PushVideoFrame 方法推送自定义输入的视频帧
     */
    public native void publishCustomVideo(int w, int h, int fps, int bitrate);

    /**
     * \brief 取消自定义视频输入流的发布
     */
    public native void unPublishCustomVideo();

    /**
     * \brief 打开麦克风并发布音频流
     *
     * \param error_cb 错误回调
     */
//    public native void publishMicrophone(ErrorCallback errorCallback);

    /**
     * \brief 取消发布麦克风音频流
     */
    public native void unPublishMicrophone();

    /**
     * \brief 暂停媒体流轨道的生产或消费
     *  - 当 track 是远端媒体流时, 将会暂停消费远端媒体
     *  - 当 track 是本地媒体流时, 将会暂停生产本地媒体
     *
     * \param track [视频/音频轨道](<> "zwrtc::Track")
     */
    public native void pause(Track track);

    /**
     * \brief 恢复媒体流轨道的生产或消费
     *  - 当 track 是远端媒体流时, 将会恢复消费远端媒体
     *  - 当 track 是本地媒体流时, 将会恢复生产本地媒体
     *
     * \param track [视频/音频轨道](<> "zwrtc::Track")
     */
    public native void resume(Track track);
    public native void setVideoFrameListener(long trackAdr);
    public native void pushVideoFrame(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timesTampUs);
    public native void pushVideoFrame2(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timeTampUs, int rotation);
    public native void yuvI420bufferSave(byte[] I420Data);
    public native void destroyNativeObject();
}
