package com.zwrtc;

import static com.zwrtc.Constant.INTERNET_RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.zwrtc.utils.Utils.convertNV21BufferToVideoFrame;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import org.webrtc.SurfaceViewRenderer;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    private SurfaceViewRenderer mLocalVideoView;
    private SurfaceViewRenderer mRemoteVideoView;

    private RoomClient mRoomClient;
    private RoomStore mRoomStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoomStore = new ViewModelProvider(this).get(RoomStore.class);
        mRoomClient = new RoomClient(this, mRoomStore);
        Log.i(TAG, "TestActivity prepare initViews()");
        initViews();

        Log.i(TAG, "TestActivity prepare initObServer()");
        initObServer();
        Log.i(TAG, "TestActivity prepare checkAndRequestPermissions()");
        checkAndRequestPermissions();
        Log.i(TAG, "TestActivity succeed");

    }

    private void initViews() {
        mLocalVideoView = findViewById(R.id.local_view);
        mRemoteVideoView = findViewById(R.id.remote_view);
        mLocalVideoView.init(PeerConnectionUtils.getEglContext(), null);
        mRemoteVideoView.init(PeerConnectionUtils.getEglContext(), null);
    }

    private int i = 0;

    private void initObServer() {
        mRoomStore.targetTrack.observe(this, videoTrack -> {
            if (videoTrack != null) {
                videoTrack.addSink(mLocalVideoView);
                videoTrack.addSink(videoFrame -> {
                    mLocalVideoView.onFrame(videoFrame);
                    if (i < 100) {
                        mRoomClient.pushVideoFrame(videoFrame);
                        i++;
                    }
                });
            }
        });

        mRoomStore.remoteVideoTrack.observe(this, videoFrame -> {
            if (videoFrame != null) {
                mRemoteVideoView.onFrame(convertNV21BufferToVideoFrame(videoFrame));
            }
        });
    }

    private void checkAndRequestPermissions() {
        boolean hasInternetPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
        boolean hasRecordAudioPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        if (hasInternetPermission && hasRecordAudioPermission) {
            //enableCamera();
            mRoomClient.initRTC(); // 替换为需要执行的网络和录音操作
        } else {
            List<String> permissionsToRequest = new ArrayList<>();

            if (!hasInternetPermission) {
                permissionsToRequest.add(Manifest.permission.INTERNET);
            }

            if (!hasRecordAudioPermission) {
                permissionsToRequest.add(Manifest.permission.RECORD_AUDIO);
            }
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), INTERNET_RECORD_AUDIO_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INTERNET_RECORD_AUDIO_PERMISSION_REQUEST_CODE) {
            boolean hasInternetPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
            boolean hasRecordAudioPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

            if (hasInternetPermission && hasRecordAudioPermission) {
                mRoomClient.initRTC(); // 替换为需要执行的网络和录音操作
            } else {
                Log.d("YourTag", "Internet or record audio permission not granted.");
            }
        }
    }
}
