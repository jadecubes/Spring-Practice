package com.example.demo.service.processing;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
public class Context {
    private ConcurrentHashMap<String,Processor> emps;
    private ConcurrentHashMap<String,Processor> seniorEmps;
    private ConcurrentLinkedQueue<Task> tasksForEmps;
    private ConcurrentLinkedQueue<Task> tasksForSeniorEmps;
    private ConcurrentLinkedQueue<Task> allTasks;
    private ConcurrentLinkedQueue<Transaction> transactions;
    private TransactionService service;

    public ConcurrentHashMap<String, Processor> getEmps() {
        return emps;
    }

    public ConcurrentHashMap<String, Processor> getSeniorEmps() {
        return seniorEmps;
    }

    public ConcurrentLinkedQueue<Task> getTasksForEmps() {
        return tasksForEmps;
    }

    public ConcurrentLinkedQueue<Task> getTasksForSeniorEmps() {
        return tasksForSeniorEmps;
    }

    public ConcurrentLinkedQueue<Task> getAllTasks() {
        return allTasks;
    }

    public ConcurrentLinkedQueue<Transaction> getTransactions() {
        return transactions;
    }

    public TransactionService getService() {
        return service;
    }

    public Context(ConcurrentLinkedQueue<Task> allTasks,
                   ConcurrentLinkedQueue<Task> tasksForEmps,
                   ConcurrentLinkedQueue<Task> tasksForSeniorEmps,
                   ConcurrentHashMap<String, Processor> emps,
                   ConcurrentHashMap<String, Processor> seniorEmps,
                   TransactionService service){
        this.emps=emps;
        this.seniorEmps=seniorEmps;
        this.allTasks=allTasks;
        this.tasksForEmps=tasksForEmps;
        this.tasksForSeniorEmps=tasksForSeniorEmps;
        this.transactions=transactions;
        this.service=service;
    }
}
