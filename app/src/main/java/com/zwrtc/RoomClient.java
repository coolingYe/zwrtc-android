package com.zwrtc;

import static com.zwrtc.jni.Constants.ROOM_ID;
import static com.zwrtc.jni.Constants.SERVICE_URL;
import static com.zwrtc.jni.Constants.VIDEO_FPS;
import static com.zwrtc.jni.Constants.VIDEO_HEIGHT;
import static com.zwrtc.jni.Constants.VIDEO_RATE;
import static com.zwrtc.jni.Constants.VIDEO_WIDTH;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.zwrtc.jni.Constants;
import com.zwrtc.jni.IRtcEngineEventHandler;
import com.zwrtc.jni.RTCClient;
import com.zwrtc.jni.track.Track;
import com.zwrtc.jni.type.ConnectionClosedInfo;
import com.zwrtc.jni.type.RTCClientConfig;
import com.zwrtc.jni.type.RoomState;
import com.zwrtc.jni.utils.CommonUtils;
import com.zwrtc.jni.utils.PeerConnectionUtils;

import org.webrtc.VideoFrame;
import org.webrtc.VideoTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RoomClient {
    private static final String TAG = "RoomClient";
    private Context context;
    private RoomStore mRoomStore;
    private Handler mWorkHandler;
    private PeerConnectionUtils mPeerConnectionUtils;
    private RTCClient mRtcClient;
    private RTCClientConfig config;

    private VideoTrack mVideoTrack;

    public RoomClient(Context context, RoomStore mRoomStore) {
        this.context = context;
        this.mRoomStore = mRoomStore;

        HandlerThread handlerThread = new HandlerThread("worker");
        handlerThread.start();
        mWorkHandler = new Handler(handlerThread.getLooper());
        mWorkHandler.post(() -> mPeerConnectionUtils = new PeerConnectionUtils());
    }

    public void initRTC() {
        Log.i(TAG, "initialize after");

        config = new RTCClientConfig();
        config.mRoomId = ROOM_ID;
        config.mServerUrl = SERVICE_URL;
        config.mChannelProfile = Constants.CHANNEL_PROFILE_LIVE_COMMUNICATION;
        config.mRtcEngineEventHandler = eventHandler;
        config.mAppId = "pAoK7BnE";
        config.mUserToken = "pAoK7BnE.RbdQI2VyEwbyVI8w2kIBUUjJFpXgWhhaQiPSWglvY7M=.eyJhcHBJZCI6InBBb0s3Qm5FIiwiZXhwaXJlQXQiOjE2OTU5MTY4MDAwMDAsInBlcm1pc3Npb24iOiJ1c2VyIiwicm9vbUlkIjoiMTIzNDU2Iiwic2VydmljZUNvZGUiOiJydGNfdmlkZW9fY2FsbCJ9";
        config.mDisplayName = CommonUtils.getRandomString(8);
        config.mUserId = CommonUtils.getRandomString(8);

        mRtcClient = RTCClient.create(config);
        mRtcClient.joinChannel(config);
    }

    public void saveYuvDataFile(VideoFrame videoFrame) {
        // 获取外部存储目录路径
        File externalStorageDir = this.context.getExternalCacheDir();
        Log.d(TAG, "saveYuvDataFile externalStorageDir=" + externalStorageDir);

        // YUV文件路径
        String fileName = "test";
        File file = new File(externalStorageDir, fileName + ".yuv");

        try {
            // 已存在则清空文件
            if(file.exists()) {
                file.createNewFile();
            }

            // 获取I420Buffer
            VideoFrame.I420Buffer buffer = videoFrame.getBuffer().toI420();

            int height = buffer.getHeight();
            int width = buffer.getWidth();

            int ySize = height * width;
            int uSize = ySize / 4;
            int vSize = ySize / 4;

            // 分配YUV数组
            byte[] yData = new byte[ySize];
            byte[] uData = new byte[uSize];
            byte[] vData = new byte[vSize];

            buffer.getDataY().get(yData);
            buffer.getDataU().get(uData);
            buffer.getDataV().get(vData);

            // 写文件流
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(yData);
            fos.write(uData);
            fos.write(vData);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[][] convertI420BufferToByteArrayNew(VideoFrame videoFrame) {
        // 获取I420Buffer
        VideoFrame.I420Buffer buffer = videoFrame.getBuffer().toI420();

        int height = buffer.getHeight();
        int width = buffer.getWidth();

        int ySize = height * width;
        int uSize = ySize / 4;
        int vSize = ySize / 4;

        // 分配YUV数组
        byte[] yData = new byte[ySize];
        byte[] uData = new byte[uSize];
        byte[] vData = new byte[vSize];

        buffer.getDataY().get(yData);
        buffer.getDataU().get(uData);
        buffer.getDataV().get(vData);

        byte[][] yuvData = {yData, uData, vData};
        return yuvData;
    }


    public void pushVideoFrame(VideoFrame videoFrame) {
        //saveYuvDataFile(videoFrame);
        byte[][] yuvData = convertI420BufferToByteArrayNew(videoFrame);
        byte[] yData = yuvData[0];
        byte[] uData = yuvData[1];
        byte[] vData = yuvData[2];

//        mRtcClient.pushVideoTrack(videoFrame.getBuffer().getWidth(), videoFrame.getBuffer().getHeight(),
//                                yData, videoFrame.getBuffer().toI420().getStrideY(),
//                                uData, videoFrame.getBuffer().toI420().getStrideU(),
//                                vData, videoFrame.getBuffer().toI420().getStrideV(), (videoFrame.getTimestampNs() / 1000));
    }

    private void enableCamera() {
        mWorkHandler.post(() -> {
            PeerConnectionUtils.setPreferCameraFace("front");
            mVideoTrack = (mPeerConnectionUtils.createVideoTrack(context, "video1"));
            mVideoTrack.setEnabled(true);
            mRoomStore.targetTrack.postValue(mVideoTrack);
        });
    }

    IRtcEngineEventHandler eventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onRoomStateChanged(RoomState state, ConnectionClosedInfo closedInfo) {
            super.onRoomStateChanged(state, closedInfo);
//            Log.i(TAG, "onRoomStateChanged callback succeed");
            Log.i(TAG, "onRoomStateChanged state = " + state);
            if (state == RoomState.CONNECTED) {
                mRtcClient.initPublishVideo(VIDEO_WIDTH, VIDEO_HEIGHT, VIDEO_FPS, VIDEO_RATE);
            }
        }

        @Override
        public void onUserJoined(String remoteUserId, String displayName, String userData) {
            super.onUserJoined(remoteUserId, displayName, userData);
            Log.i(TAG, "onUserJoined callback succeed");
        }

        @Override
        public void onUserLeft(String remoteUserId) {
            super.onUserLeft(remoteUserId);
//            Log.i(TAG, "onUserLeft callback succeed");
        }

        @Override
        public void onLocalTrackCreate(Track track) {
            super.onLocalTrackCreate(track);
//            Log.i(TAG, "onLocalTrackCreate callback succeed");
            Log.i(TAG, "onLocalTrackCreate ptr = " + track.nativeTrackPtr);
            if (track.isVideo) {
                mRtcClient.setOnVideoFrameListener(track.nativeTrackPtr);
                enableCamera();
            }
        }

        @Override
        public void onLocalTrackRemove(Track track) {
            super.onLocalTrackRemove(track);
            Log.i(TAG, "onLocalTrackRemove callback succeed");
        }

        @Override
        public void onRemoteTrackCreate(Track track) {
            super.onRemoteTrackCreate(track);
//            Log.i(TAG, "onRemoteTrackCreate callback succeed");
            Log.i(TAG, "onRemoteTrackCreate ptr = " + track.nativeTrackPtr);
            if (track.isVideo) {
                Log.i("onRemoteTrackCreate", "onRemoteTrackCreate  isVideo inro ptr=");
                mRtcClient.setOnVideoFrameListener(track.nativeTrackPtr);

            }
        }

        @Override
        public void onRemoteTrackRemove(Track track) {
            super.onRemoteTrackRemove(track);
//            Log.i(TAG, "onRemoteTrackRemove callback succeed");
        }


        @Override
        public void onVideoFrame(String user_id, String track_id, com.zwrtc.jni.type.VideoFrame video_frame) {
            super.onVideoFrame(user_id, track_id, video_frame);
            Log.i("onVideoFrame", "onVideoFrame callback succeed MainActivity" + track_id);
            Log.i("onVideoFrame", "onVideoFrame userId=" + user_id);
            if (Objects.equals(user_id, config.getUserId())) {
                //当地的用户
                Log.i(TAG, "onVideoFrame local user into");
//                mRoomStore.remoteVideoTrack.postValue(video_frame);
            } else {
                //远程的用户
                Log.i(TAG, "onVideoFrame remote user into");
                mRoomStore.remoteVideoTrack.postValue(video_frame);
            }
        }
    };
}
