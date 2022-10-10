package com.example.demo.service.processing;

import org.apache.tomcat.jni.Proc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
//https://www.educative.io/answers/how-to-get-the-state-of-a-thread-in-java
public class TaskDispatcher extends Thread{
    private Context context;
    public TaskDispatcher(Context context){
        this.context=context;
    }
    public void run() {
        while(true){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (TaskDispatcher.class) {
                Map<String, Processor> sEmp = context.getSeniorEmps();
                Map<String, Processor> emp = context.getEmps();
                Collection tasks4senior = context.getTasksForSeniorEmps();
                Collection tasks4junior = context.getTasksForEmps();
                ConcurrentLinkedQueue all=context.getAllTasks();

                for (String k : emp.keySet()) {
                     Processor p=emp.get(k);
                     if (!p.isProcessing() && all.size()>0 ) {
                         //System.out.println("\u001B[45m" +  "dispatcher: add to emp task que" + "\u001B[0m");
                         tasks4junior.add(all.poll());
                     }
                 }
                 for (String k : sEmp.keySet()) {
                     Processor p=sEmp.get(k);
                     if (!p.isProcessing() && all.size()>0 ){
                         //System.out.println("\u001B[45m" +  "dispatcher: add to senior emp task que" + "\u001B[0m");
                         tasks4senior.add(all.poll());
                     }
                  }

            }
        }
    }
}
