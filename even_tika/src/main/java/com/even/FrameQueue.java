package com.even;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.bytedeco.javacv.Frame;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/2/15
 */
public class FrameQueue {
    private Frame[] frames;
    private Integer head = 0;
    private Integer tail = 0;
    private int n = 0;
    private boolean status = false;

    public FrameQueue(int capacity) {
        this.frames = new Frame[capacity];
        this.n = capacity;
        status = true;
    }

    public boolean enqueue(Frame frame) {
        /*表示队列已满*/
//        if (tail == n) return false;
        synchronized (head) {
            if (tail == n) {
                if (head == 0)
                    return false;
                else {
                    for (int i = head; i < tail; ++i) {
                        frames[i - head] = frames[i];
                    }
                    tail -= head;
                    head = 0;
                }
            }
            frames[tail] = frame;
            ++tail;
        }
        return true;
    }

    public Frame dequeue() {
        synchronized (head) {
        /*表示队列为空*/
            if (Objects.equals(head, tail)) return null;
            Frame frame = frames[head];
            ++head;
            return frame;
        }
    }

    public void close() {
        this.status = false;
    }

    public boolean isUse() {
        return this.status;
    }
}
