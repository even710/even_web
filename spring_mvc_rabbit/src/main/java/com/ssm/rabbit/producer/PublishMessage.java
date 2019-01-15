package com.ssm.rabbit.producer;

import java.io.Serializable;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/15
 */
public class PublishMessage implements Serializable {
    private int id;
    private String body;

    public PublishMessage(int i, Object message) {
        this.id = i;
        this.body = (String) message;
    }

    public PublishMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
