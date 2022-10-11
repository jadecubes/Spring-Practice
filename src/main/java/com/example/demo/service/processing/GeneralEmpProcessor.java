package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GeneralEmpProcessor extends Processor {
    public GeneralEmpProcessor(Context context){initProcessor( context,  Type.GENERAL);}
    @Override
    public void run() {
        while (true) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //https://jenkov.com/tutorials/java-concurrency/synchronized.html
            synchronized (GeneralEmpProcessor.class) {
                Context context=getContext();
                ConcurrentLinkedQueue<Task> taskQue =context.getTasksForEmps();
                process(taskQue);
            }
        }
    }
}
