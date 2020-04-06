package com.even;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/2/15
 */
public class TencentAi {

    static FrameQueue frameQueue = new FrameQueue(10);
//    static volatile Frame needFrame;

    public static void main(String[] args) throws IOException, InterruptedException {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        long start = new Date().getTime();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FaceRecognizeService faceRecognizeService = new FaceRecognizeService();
                while (true) {
                    Frame frame = frameQueue.dequeue();
                    if (!frameQueue.isUse())
                        break;
                    if (frame != null) {
                        faceRecognizeService.faceRecognize(frame);
                    }
                }
            }
        });
        thread.setName("人脸识别");
        thread.start();
        while (true) {
            if (!canvas.isDisplayable()) {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(2);//退出
                frameQueue.close();
                break;
            }
            Frame frame = grabber.grab();
            canvas.showImage(frame);//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

            long end = new Date().getTime();
            long time = (end - start) / 1000;
            if (time > 4) {
                start = end;
                frameQueue.enqueue(frame);
            }
        }
    }
}
