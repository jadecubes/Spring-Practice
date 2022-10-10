package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SeniorEmpProcessor extends Processor{
    public SeniorEmpProcessor(Context context){
        super( context,  Type.SENIOR);
    }
    @Override
    public void run() {
        while (true) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //https://jenkov.com/tutorials/java-concurrency/synchronized.html
            synchronized (SeniorEmpProcessor.class) {
                Thread curThread=java.lang.Thread.currentThread();
                Context context=super.getContext();
                ConcurrentLinkedQueue<Task> taskQue =context.getTasksForSeniorEmps();

                if (taskQue.size() > 0) {
                    //System.out.println("\u001B[43m" + "start... "+type +": " + curThread.getName() + " writes to db." + "\u001B[0m");
                    super.setProcessing(true);
                    Task t = taskQue.poll();
                    Transaction ts = new Transaction();

                    ts.setBusiness_time(t.getBusiness_time());
                    ts.setTransaction_id(t.getTransaction_id());
                    ts.setPoint_amount(t.getPoint_amount());
                    ts.setProcess_cost(t.getProcess_cost());
                    ts.setCreated_by(curThread.getName());
                    ts.setCreated_time(new Date());
                    context.getService().save(ts);
                    super.setProcessing(false);
                    System.out.println("\u001B[44m" +  "SENIOR: " + curThread.getName() + " writes to db." + "\u001B[0m");
                }
            }
        }
    }
}
