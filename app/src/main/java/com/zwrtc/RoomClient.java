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

        mRtcClient = RTCClient.create(config);
        mRtcClient.joinChannel(config);
    }


    public void saveYuvDataFile(VideoFrame videoFrame) {
        // 获取外部存储目录路径
        File externalStorageDir = this.context.getExternalCacheDir();
        Log.d("saveYuvDataFile", "externalStorageDir=" + externalStorageDir);

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


//            ByteBuffer rotationBuffer = ByteBuffer.allocate(height * width * 3 / 2);
//            YuvHelper.I420Rotate(buffer.getDataY(), buffer.getStrideY(), buffer.getDataU(), buffer.getStrideU(),
//                    buffer.getDataV(), buffer.getStrideV(), rotationBuffer, width, height, 270);

            // 分配YUV数组
            byte[] yData = new byte[ySize];
            byte[] uData = new byte[uSize];
            byte[] vData = new byte[vSize];

            buffer.getDataY().get(yData);
            buffer.getDataU().get(uData);
            buffer.getDataV().get(vData);

//            rotationBuffer.rewind();
//            rotationBuffer.get(yData);
//            rotationBuffer.get(uData);
//            rotationBuffer.get(vData);

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

    public void pushVideoFrame(VideoFrame videoFrame) {

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
            Log.i("onRoomStateChanged", "onRoomStateChanged callback succeed MainActivity");
            Log.i("onRoomStateChanged", "onRoomStateChanged state = " + state);
            if (state == RoomState.CONNECTED) {
                mRtcClient.initPublishVideo(VIDEO_WIDTH, VIDEO_HEIGHT, VIDEO_FPS, VIDEO_RATE);
            }
        }

        @Override
        public void onUserJoined(String remoteUserId, String displayName, String userData) {
            Log.i("onUserJoined", "onUserJoined callback succeed MainActivity");
        }

        @Override
        public void onUserLeft(String remoteUserId) {
            Log.i("onUserLeft", "onUserLeft callback succeed MainActivity");
        }

        @Override
        public void onLocalTrackCreate(Track track) {
            super.onLocalTrackCreate(track);
            Log.i("onLocalTrackCreate", "onLocalTrackCreate callback succeed MainActivity");
            Log.i("onLocalTrackCreate", "onLocalTrackCreate callback id=" + track.isVideo);
            Log.i("onLocalTrackCreate", "onLocalTrackCreate ptr=" + track.nativeTrackPtr);
            if (track.isVideo) {
                mRtcClient.setOnVideoFrameListener(track.nativeTrackPtr);
                enableCamera();
            }

        }

        @Override
        public void onLocalTrackRemove(Track track) {
            super.onLocalTrackRemove(track);
            Log.i("onLocalTrackRemove", "onLocalTrackRemove callback succeed MainActivity");
        }

        @Override
        public void onRemoteTrackCreate(Track track) {
            super.onRemoteTrackCreate(track);
            Log.i("onRemoteTrackCreate", "onRemoteTrackCreate callback succeed MainActivity");
            Log.i("onRemoteTrackCreate", "onRemoteTrackCreate ptr=" + track.nativeTrackPtr);
            if (track.isVideo) {
                Log.i("onRemoteTrackCreate", "onRemoteTrackCreate  isVideo inro ptr=");
                mRtcClient.setOnVideoFrameListener(track.nativeTrackPtr);

            }
        }

        @Override
        public void onRemoteTrackRemove(Track track) {
            super.onRemoteTrackRemove(track);
        }

        @Override
        public void onVideoFrame(String user_id, String track_id, VideoFrame video_frame) {
            super.onVideoFrame(user_id, track_id, video_frame);
            Log.i("onVideoFrameCallback", "onVideoFrameCallback callback succeed MainActivity" + video_frame.toString());
        }

        @Override
        public void onVideoFrame(String user_id, String track_id, com.zwrtc.jni.type.VideoFrame video_frame) {
            super.onVideoFrame(user_id, track_id, video_frame);
            Log.i("onVideoFrame", "onVideoFrame callback succeed MainActivity" + track_id);
            Log.i("onVideoFrame", "onVideoFrame userId=" + user_id);
            if (Objects.equals(user_id, config.getUserId())) {
                //当地的用户
                Log.i("onVideoFrame", "onVideoFrame local user into");
//              mRoomStore.remoteVideoTrack.postValue(video_frame);
            } else {
                //远程的用户
                Log.i("onVideoFrame", "onVideoFrame remote user into");
                mRoomStore.remoteVideoTrack.postValue(video_frame);
            }
        }
    };
}
