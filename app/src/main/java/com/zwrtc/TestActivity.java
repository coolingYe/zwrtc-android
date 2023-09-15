package com.zwrtc;

import static com.zwrtc.utils.Utils.convertNV21BufferToVideoFrame;

import android.Manifest;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.zwrtc.jni.utils.PeerConnectionUtils;

import org.webrtc.SurfaceViewRenderer;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class TestActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

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
        initViews();
        checkPermissions();
        initObServer();
    }

    private void initViews() {
        mLocalVideoView = findViewById(R.id.local_view);
        mRemoteVideoView = findViewById(R.id.remote_view);
        mLocalVideoView.init(PeerConnectionUtils.getEglContext(), null);
        mRemoteVideoView.init(PeerConnectionUtils.getEglContext(), null);
    }

    private void initObServer() {
        mRoomStore.targetTrack.observe(this, videoTrack -> {
            if (videoTrack != null) {
                videoTrack.addSink(videoFrame -> {
                    mLocalVideoView.onFrame(videoFrame);
                    mRoomClient.pushVideoFrame(videoFrame);
                });
            }
        });

        mRoomStore.remoteVideoTrack.observe(this, videoFrame -> {
            if (videoFrame != null) {
                mRemoteVideoView.onFrame(convertNV21BufferToVideoFrame(videoFrame));
            }
        });
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            Log.d("checkCameraPermissions", "No Permissions");
            EasyPermissions.requestPermissions(this, "Please provide permissions", 1, permissions);
        } else {
            mRoomClient.initRTC();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(ContentValues.TAG, "Permission request successful");
        mRoomClient.initRTC();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(ContentValues.TAG, "Permission request failed");
    }
}
