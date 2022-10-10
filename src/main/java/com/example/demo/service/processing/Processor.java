package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Processor extends Thread {
    private boolean isProcessing;

    public enum Type {
        SENIOR,
        GENERAL
    }

    private Context context;
    private Type type;
    private Object lock;
    public Type getType() {

        return type;
    }

    public Processor(Context context, Type type) {
        this.context = context;
        isProcessing = false;
        this.type=type;
        lock = new Object();
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
    }

    public Context getContext() {
        return context;
    }
}
