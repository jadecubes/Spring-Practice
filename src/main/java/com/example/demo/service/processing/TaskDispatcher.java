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
    public void dispatch(Map<String, Processor> processors,ConcurrentLinkedQueue<Task> sourceTasks,ConcurrentLinkedQueue<Task> destTasks){
        for (String k : processors.keySet()) {
            Processor p=processors.get(k);
            if (!p.isOccupied() && sourceTasks.size()>0 ) {
                if(p.getType()== Processor.Type.SENIOR)
                    System.out.println("\u001B[45m" +  "dispatch to SENIOR: "+p.getName()+ " task que" + "\u001B[0m");
                else
                    System.out.println("\u001B[45m" +  "dispatch to GENERAL: "+p.getName()+ " task que" + "\u001B[0m");
                p.setState(Processor.ProcessorState.SCHEDULED_TO_PROCCESS);
                destTasks.add(sourceTasks.poll());
            }
        }
    }
    public void run() {
        while(true){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (TaskDispatcher.class) {
                if(context==null) break;

                Map<String, Processor> sEmp = context.getSeniorEmps();
                Map<String, Processor> emp = context.getEmps();
                ConcurrentLinkedQueue tasks4senior = context.getTasksForSeniorEmps();
                ConcurrentLinkedQueue tasks4junior = context.getTasksForEmps();
                ConcurrentLinkedQueue all=context.getAllTasks();
                dispatch(emp,all,tasks4junior);
                dispatch(sEmp,all,tasks4senior);
            }
        }
    }
}
