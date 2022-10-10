package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
public class ProcessorServiceImpl implements ProcessorService{
    @Autowired
    private TransactionService service;
    Thread taskDispather;

    private ConcurrentHashMap<String,Processor> emps;
    private ConcurrentHashMap<String,Processor> seniorEmps;
    private ConcurrentLinkedQueue<Task> tasksForEmps;
    private ConcurrentLinkedQueue<Task> tasksForSeniorEmps;
    private ConcurrentLinkedQueue<Task> allTasks;
    public ProcessorServiceImpl(TransactionService service){
        this.service=service;
        allTasks=new ConcurrentLinkedQueue<>();
        tasksForSeniorEmps=new ConcurrentLinkedQueue<>();
        tasksForEmps=new ConcurrentLinkedQueue<>();
        seniorEmps=new ConcurrentHashMap<>();
        emps=new ConcurrentHashMap<>();

        //set senior emp
        Context context=new Context(allTasks,tasksForEmps,tasksForSeniorEmps,emps,seniorEmps,service);
        taskDispather=new TaskDispatcher(context);

        //senior: Debra
        SeniorEmpProcessor senior=new SeniorEmpProcessor(context);
        senior.setName("Debra");
        seniorEmps.put(senior.getName(),senior);
        senior.start();

        //general: EMP1
        GeneralEmpProcessor general=new GeneralEmpProcessor(context);
        general.setName("EMP1");
        emps.put("EMP1",general);
        general.start();

        //general: EMP2
        general=new GeneralEmpProcessor(context);
        general.setName("EMP2");
        emps.put("EMP2",general);
        general.start();

        taskDispather.start();

    }

    @Override
    public void processTask(Task task) {
        allTasks.add(task);
    }

}
