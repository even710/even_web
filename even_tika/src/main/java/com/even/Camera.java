package com.even;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/2/14
 */
public class Camera {
    //    private static String APP_ID = "15555933";
    private static String APP_ID = "15559095";
    //    private static String API_KEY = "W8K4zq2zrmIN9IhA9OXhkwRU";
    private static String API_KEY = "TYNX7L6VruWxzdEE8cIgXGws";
    //    private static String SECRET_KEY = "Bmn9S44YRdOuYUK8yRb4n7ckNGwFl0Ud ";
    private static String SECRET_KEY = "fV6MwaHvYQ2ik9K0O4l5K44SgMfwTnea";

    public static void main(String[] args) throws IOException, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        while (true) {
            if (!canvas.isDisplayable()) {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(2);//退出
                break;
            }
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            Frame frame = grabber.grab();
            opencv_core.IplImage image = converter.convert(frame);
            Frame frame1 = converter.convert(image);

            Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
            BufferedImage bufferedImage = java2dFrameConverter.convert(frame1);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
            byte[] data = out.toByteArray();
            // 初始化一个AipFace
//            detect(Base64Util.encode(data));
            HashMap<String, String> options = new HashMap<>();
            options.put("type", "gender");
            options.put("max_face_num", "10");
            AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
            System.out.println(client.bodyAttr(data, options));
            canvas.showImage(frame1);//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            image.release();
            Thread.sleep(200);//50毫秒刷新一次图像
        }
    }


    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "W8K4zq2zrmIN9IhA9OXhkwRU";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "Bmn9S44YRdOuYUK8yRb4n7ckNGwFl0Ud";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String detect(String image) {
        // 请求url
//        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_attr";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
//            map.put("face_field", "faceshape,facetype,age");
            map.put("type", "");

//            map.put("max_face_num", 10);
//            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth(API_KEY, SECRET_KEY);

//            String result = HttpUtil.post(url, accessToken, "application/json", param);
            String result = HttpUtil.post(url, accessToken, "application/x-www-form-urlencoded", param);
//            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
