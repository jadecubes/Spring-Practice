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
    public Type getType() {

        return type;
    }
    public Processor(){
        context=null;
        state = ProcessorState.IDLE;
        type=Type.GENERAL;
    }

    public void initProcessor(Context context,Type type){
        this.context = context;
        state = ProcessorState.IDLE;
        this.type=type;
    }
    public boolean isOccupied() {
        return (state==ProcessorState.IS_PROCESSING || state==ProcessorState.SCHEDULED_TO_PROCCESS);
    }

    public void setState(ProcessorState state) {
        this.state = state;
    }

    public ProcessorState getProcessorState() {
        return state;
    }

    public Context getContext() {
        return context;
    }
    public void process(ConcurrentLinkedQueue<Task> taskQue){
        if (taskQue.size() > 0) {
            if(context==null || context.getService()==null) return;

            if(type==Type.GENERAL)
                System.out.println("\u001B[43m" + "GENERAL: "  + currentThread().getName() + " starts writing." + "\u001B[0m");
            else
                System.out.println("\u001B[44m" + "SENIOR: "  + currentThread().getName() + " starts writing." + "\u001B[0m");

            setState(ProcessorState.IS_PROCESSING);
            Task t = taskQue.poll();
            Transaction ts = new Transaction();

            try {
                sleep(sleepTime(t));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            t.setProcessor_name(currentThread().getName());
            ts.setValues(t);
            context.getService().save(ts);

            setState(ProcessorState.IDLE);
            if(type==Type.GENERAL)
                System.out.println("\u001B[43m" + "GENERAL: "  + currentThread().getName() + " writes to db." + "\u001B[0m");
            else
                System.out.println("\u001B[44m" + "SENIOR: "  + currentThread().getName() + " writes to db." + "\u001B[0m");
        }
    }
    public int sleepTime(Task t) {
        if(type==Type.GENERAL)
            return (t.getProcess_cost());
        else
            return 0;
    }
}
