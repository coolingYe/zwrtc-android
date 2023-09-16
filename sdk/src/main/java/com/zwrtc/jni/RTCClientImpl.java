package com.zwrtc.jni;

import androidx.annotation.NonNull;

import com.zwrtc.jni.track.Track;
import com.zwrtc.jni.type.CreateFusionRoomConfig;
import com.zwrtc.jni.type.FusionUserConfig;
import com.zwrtc.jni.type.LogLevel;
import com.zwrtc.jni.type.Property;
import com.zwrtc.jni.type.RTCClientConfig;

public class RTCClientImpl extends RTCClient {
    static {
        System.loadLibrary("zwrtc");
    }

    private static final String TAG = "RTCClient";

    private final RTCClientConfig rtcClientConfig;

    public RTCClientImpl(@NonNull RTCClientConfig config) {
        this.rtcClientConfig = config;

        createNativeObject(rtcClientConfig);
        setProperties(rtcClientConfig);
    }

    public RTCClientConfig getRtcClientConfig() {
        return rtcClientConfig;
    }

    @Override
    public void joinChannel(RTCClientConfig config) {
        this.join(config);
    }

    @Override
    public void leaveChannel() {
        this.leave();
    }

    @Override
    public void initPublishVideo(int w, int h, int fps, int bitrate) {
        this.publishCustomVideo(w, h, fps, bitrate);
    }

    @Override
    public void pushVideoTrack(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timesTampUs) {
        this.pushVideoFrame(w, h, dataY, strideY, dataU, strideU, dataV, strideV, timesTampUs);
    }

    @Override
    public void setOnVideoFrameListener(long trackAdr) {
        this.setVideoFrameListener(trackAdr);
    }

    private void createNativeObject(RTCClientConfig config) {
        if (config != null) {
            this.createNativeObject(rtcClientConfig.mRtcEngineEventHandler);
        }
    }

    private void setProperties(RTCClientConfig config) {
        if (config != null) {
            Property property = new Property();
            if (config.mChannelProfile == Constants.CHANNEL_PROFILE_LIVE_CLOUD_GROUP) {
                property.protocol = Property.Protocol.kProtoo;
                property.groupPhoto = true;
            } else {
                property.protocol = Property.Protocol.kZWAI;
                property.groupPhoto = false;
            }
            property.caFile = "NONE";
            this.setProperties(property);
        }
    }

    private native void createNativeObject(IRtcEngineEventHandler rtcEngineEventHandler);

    private native void setProperties(Property properties);

    private native void join(RTCClientConfig config);

    private native void leave();

    /**
     * \brief 当有远端用户发布媒体流时，自动订阅
     */
    private native void autoSubscribe(boolean video, boolean audio);

    /**
     * \brief 加入云合影音视频房间;
     *  - 房间不存在时创建房间并加入;
     *  - 房间已存在时, 直接加入, 此时 fusionConfig 参数不生效
     *
     * \param config 基本房间信息
     * \param fusionConfig 云合影房间信息
     */
    private native void joinFusionRoom(RTCClientConfig config, CreateFusionRoomConfig fusionConfig);

    /**
     * \brief 开启云合影模式; 发布视频媒体时开启融合
     */
    private native void startFusion();

    /**
     * \brief 用在取消视频媒体发布时退出融合
     */
    private native void userExitFusion();

    /**
     * \brief 关闭房间时停止融合
     */
    private native void stopFusion();

    /**
     * \brief 更新用户融合合影信息
     *
     * \param fusion_cfg 用户融合信息
     */
    private native void changeFusionUserConfig(FusionUserConfig fusionConfig);

    /**
     * \brief 更换云合影房间的背景
     *
     * \param image_url 背景图片URL地址
     */
    private native void changeFusionBackground(String imageUrl);

    /**
     * \brief 根据索引打开摄像头并发布视频流
     *
     * \param width_cap 宽
     * \param height_cap 高
     * \param fps_cap 帧率
     * \param index 摄像头索引
     * \param error_cb 错误回调
     */
    private native void publishCameraWithIndex(int widthCap, int heightCap, int fpsCap, int index);

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
    private native void publishCustomVideo(int w, int h, int fps, int bitrate);

    /**
     * \brief 取消自定义视频输入流的发布
     */
    private native void unPublishCustomVideo();

    /**
     * \brief 打开麦克风并发布音频流
     *
     * \param error_cb 错误回调
     */
//    public native void publishMicrophone(ErrorCallback errorCallback);

    /**
     * \brief 取消发布麦克风音频流
     */
    private native void unPublishMicrophone();

    /**
     * \brief 暂停媒体流轨道的生产或消费
     *  - 当 track 是远端媒体流时, 将会暂停消费远端媒体
     *  - 当 track 是本地媒体流时, 将会暂停生产本地媒体
     *
     * \param track [视频/音频轨道](<> "zwrtc::Track")
     */
    private native void pause(Track track);

    /**
     * \brief 恢复媒体流轨道的生产或消费
     *  - 当 track 是远端媒体流时, 将会恢复消费远端媒体
     *  - 当 track 是本地媒体流时, 将会恢复生产本地媒体
     *
     * \param track [视频/音频轨道](<> "zwrtc::Track")
     */
    private native void resume(Track track);
    private native void setVideoFrameListener(long trackAdr);
    private native void pushVideoFrame(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timesTampUs);
    private native void pushVideoFrame2(int w, int h, byte[] dataY, int strideY, byte[] dataU, int strideU, byte[] dataV, int strideV, long timeTampUs, int rotation);
    private native void yuvI420bufferSave(byte[] I420Data);
    private native void destroyNativeObject();
    public native void setLogLevel(LogLevel logLevel);
}
