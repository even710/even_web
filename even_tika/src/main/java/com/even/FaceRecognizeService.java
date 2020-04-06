package com.even;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.DetectFaceRequest;
import com.tencentcloudapi.iai.v20180301.models.DetectFaceResponse;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/2/15
 */
public class FaceRecognizeService {



    public void faceRecognize(Frame frame) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage image = converter.convert(frame);
        Frame frame1 = converter.convert(image);

        Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
        BufferedImage bufferedImage = java2dFrameConverter.convert(frame1);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] data = out.toByteArray();
        // 初始化一个AipFace
        faceRecognize(Base64Util.encode(data));
        image.release();
    }

    /**
     * 腾讯ai的人脸识别
     *
     * @param image
     */
    private static void faceRecognize(String image) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential("AKID6YlkTwTMFwSs64LHWDgs9Ug6Jj02Na3y", "rRFn6MclAKFOVR4Xqe8SkNprickPvLDw");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);

            String params = "{\"MaxFaceNum\":30,\"Image\":\"" + image + "\",\"NeedFaceAttributes\":1}";
            DetectFaceRequest req = DetectFaceRequest.fromJsonString(params, DetectFaceRequest.class);

            DetectFaceResponse resp = client.DetectFace(req);

            System.out.println(DetectFaceRequest.toJsonString(resp));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
