package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Processor extends Thread {
    public enum ProcessorState{
        IDLE,
        IS_PROCESSING,
        SCHEDULED_TO_PROCCESS
    }
    public enum Type {
        SENIOR,
        GENERAL
    }
    private ProcessorState state;
    private Context context;
    private Type type;
    private Object lock;
    public Type getType() {

        return type;
    }

    public Processor(Context context, Type type) {
        this.context = context;
        state=ProcessorState.IDLE;
        this.type=type;
        lock = new Object();
    }

    public boolean isOccupied() {
        return (state==ProcessorState.IS_PROCESSING || state==ProcessorState.SCHEDULED_TO_PROCCESS);
    }

    public void setState(ProcessorState state) {
        this.state = state;
    }

    public Context getContext() {
        return context;
    }
}
