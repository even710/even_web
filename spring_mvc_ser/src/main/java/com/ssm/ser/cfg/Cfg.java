package com.ssm.ser.cfg;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */
public class Cfg {
    public static void main(String[] args) {
        System.out.println(getProjectPath());

    }

    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }
}
