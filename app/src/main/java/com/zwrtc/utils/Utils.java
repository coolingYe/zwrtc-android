package com.zwrtc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;

import org.webrtc.NV21Buffer;
import org.webrtc.VideoFrame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Utils {

    public static byte[] i420BufferToByte(VideoFrame.I420Buffer i420Buffer) {
        final int width = i420Buffer.getWidth();
        final int height = i420Buffer.getHeight();
        final int chromaWidth = (width + 1) / 2;
        final int chromaHeight = (height + 1) / 2;
        byte yValue = 0;
        byte uValue = 0;
        byte vValue = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                yValue = i420Buffer.getDataY().get(y * i420Buffer.getStrideY() + x);
            }
        }
        for (int y = 0; y < chromaHeight; ++y) {
            for (int x = 0; x < chromaWidth; ++x) {
                uValue = i420Buffer.getDataU().get(y * i420Buffer.getStrideU() + x);
                vValue = i420Buffer.getDataV().get(y * i420Buffer.getStrideV() + x);
            }
        }
        return new byte[yValue + uValue + vValue];
    }

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

//    public static byte[][] convertI420BufferToByteArray(VideoFrame.I420Buffer i420Buffer) {
//        int width = i420Buffer.getWidth();
//        int height = i420Buffer.getHeight();
//        int chromaWidth = (width + 1) / 2;
//        int chromaHeight = (height + 1) / 2;
//
//        byte[] yData = new byte[width * height];
//        byte[] uData = new byte[chromaWidth * chromaHeight];
//        byte[] vData = new byte[chromaWidth * chromaHeight];
//
//        i420Buffer.getDataY().get(yData);
//        i420Buffer.getDataU().get(uData);
//        i420Buffer.getDataV().get(vData);
//
//        byte[][] data = {yData, uData, vData};
//        return data;
//    }


    public static byte[] convertI420BufferToByteArray(VideoFrame.I420Buffer i420Buffer) {
        // 假设你已经有了一个 I420Buffer 对象 named i420Buffer
    //try{
        int width = i420Buffer.getWidth();
        int height = i420Buffer.getHeight();

        int strideY = i420Buffer.getStrideY();
        int strideU = i420Buffer.getStrideU();
        int strideV = i420Buffer.getStrideV();

        ByteBuffer dataY = i420Buffer.getDataY();
        ByteBuffer dataU = i420Buffer.getDataU();
        ByteBuffer dataV = i420Buffer.getDataV();

        // 计算 Y 数据的总长度（包括跨距）
        int lengthY = strideY * height;
        // 计算 U 数据的总长度（包括跨距）
        int lengthU = strideU * ((width + 1) / 2);
        // 计算 V 数据的总长度（包括跨距）
        int lengthV = strideV * ((width + 1) / 2);


        // 创建用于存储 YUV 数据的字节数组
        byte[] yuvData = new byte[lengthY + lengthU + lengthV];

        // 将 Y 数据复制到 yuvData 中
        dataY.position(0);
        dataY.get(yuvData, 0, lengthY);

        // 将 U 数据复制到 yuvData 中
        dataU.position(0);
        dataU.get(yuvData, lengthY, lengthU);

        // 将 V 数据复制到 yuvData 中
        dataV.position(0);
        dataV.get(yuvData, lengthY + lengthU, lengthV);

        return yuvData;

    }


    public static VideoFrame convertNV21BufferToVideoFrame(com.zwrtc.jni.type.VideoFrame videoFrame) {
        NV21Buffer nv21Buffer = new NV21Buffer(mergeYUV420(videoFrame.yData, videoFrame.uData, videoFrame.vData, videoFrame.width, videoFrame.height), videoFrame.width, videoFrame.height, null);
        return new VideoFrame(nv21Buffer, 0, videoFrame.timestamp * 1000);
    }

    public static VideoFrame convertYUVToVideoFrame(VideoFrame videoFrame) {
        NV21Buffer nv21Buffer = new NV21Buffer(i420BufferToByte(videoFrame.getBuffer().toI420()), videoFrame.getBuffer().getWidth(), videoFrame.getBuffer().getHeight(), null);
        return new VideoFrame(nv21Buffer, 0, videoFrame.getTimestampNs());
    }

    public static Bitmap i420ToBitmap(int width, int height, int rotation, int bufferLength, byte[] buffer, int yStride, int uStride, int vStride) {
        byte[] NV21 = new byte[bufferLength];
        swapYU12toYUV420SP(buffer, NV21, width, height, yStride, uStride, vStride);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int[] strides = {yStride, yStride};
        YuvImage image = new YuvImage(NV21, ImageFormat.NV21, width, height, strides);

        image.compressToJpeg(
                new Rect(0, 0, image.getWidth(), image.getHeight()),
                100, baos);

        // rotate picture when saving to file
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static byte[] flattenByteArray(byte[][] array) {
        int totalLength = 0;
        for (byte[] subArray : array) {
            totalLength += subArray.length;
        }

        byte[] flattenedArray = new byte[totalLength];
        int currentIndex = 0;
        for (byte[] subArray : array) {
            System.arraycopy(subArray, 0, flattenedArray, currentIndex, subArray.length);
            currentIndex += subArray.length;
        }

        return flattenedArray;
    }

    public static void swapYU12toYUV420SP(byte[] yu12bytes, byte[] i420bytes, int width, int height, int yStride, int uStride, int vStride) {
        System.arraycopy(yu12bytes, 0, i420bytes, 0, yStride * height);
        int startPos = yStride * height;
        int yv_start_pos_u = startPos;
        int yv_start_pos_v = startPos + startPos / 4;
        for (int i = 0; i < startPos / 4; i++) {
            i420bytes[startPos + 2 * i + 0] = yu12bytes[yv_start_pos_v + i];
            i420bytes[startPos + 2 * i + 1] = yu12bytes[yv_start_pos_u + i];
        }
    }

    public static void saveBitmap(Bitmap bitmap, String filePath, Bitmap.CompressFormat format, int quality) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            bitmap.compress(format, quality, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] bitmapToI420(int inputWidth, int inputHeight, Bitmap scaled) {
        int[] argb = new int[inputWidth * inputHeight];
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        encodeI420(yuv, argb, inputWidth, inputHeight);
        scaled.recycle();
        return yuv;
    }

    public static void encodeI420(byte[] i420, int[] argb, int width, int height) {
        final int frameSize = width * height;

        int yIndex = 0;                   // Y start index
        int uIndex = frameSize;           // U statt index
        int vIndex = frameSize * 5 / 4; // V start index: w*h*5/4

        int a, R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                a = (argb[index] & 0xff000000) >> 24; //  is not used obviously
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff) >> 0;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                // I420(YUV420p) -> YYYYYYYY UU VV
                i420[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                if (j % 2 == 0 && i % 2 == 0) {
                    i420[uIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
                    i420[vIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
                }
                index++;
            }
        }
    }

    public static byte[] getYData(byte[] yuv420, int width, int height) {
        int dataSize = width * height;
        byte[] yData = new byte[dataSize];
        System.arraycopy(yuv420, 0, yData, 0, dataSize);
        return yData;
    }

    public static byte[] getUData(byte[] yuv420, int width, int height) {
        int dataSize = width * height / 4;
        byte[] uData = new byte[dataSize];
        int offset = width * height;
        System.arraycopy(yuv420, offset, uData, 0, dataSize);
        return uData;
    }

    public static byte[] getVData(byte[] yuv420, int width, int height) {
        int dataSize = width * height / 4;
        byte[] vData = new byte[dataSize];
        int offset = width * height + width * height / 4;
        System.arraycopy(yuv420, offset, vData, 0, dataSize);
        return vData;
    }
}
