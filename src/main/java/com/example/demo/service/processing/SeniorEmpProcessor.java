package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SeniorEmpProcessor extends Processor{
    public SeniorEmpProcessor(Context context){
        initProcessor( context,  Type.SENIOR);
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
                Context context=getContext();
                if(context==null) break;

                ConcurrentLinkedQueue<Task> taskQue =context.getTasksForSeniorEmps();
                process(taskQue);
            }
        }
    }
}
