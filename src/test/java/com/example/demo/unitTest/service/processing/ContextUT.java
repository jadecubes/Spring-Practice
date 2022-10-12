package com.example.demo.unitTest.service.processing;
import com.example.demo.service.TransactionServiceImpl;
import com.example.demo.service.processing.Context;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.service.TransactionService;
import com.example.demo.service.processing.Processor;
import com.example.demo.service.processing.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//https://stackoverflow.com/questions/55719014/beforeall-method-as-non-static
public class ContextUT {
    private static ConcurrentHashMap<String,Processor> emps;
    private static ConcurrentHashMap<String,Processor> seniorEmps;
    private static ConcurrentLinkedQueue<Task> tasksForEmps;
    private static ConcurrentLinkedQueue<Task> tasksForSeniorEmps;
    private static ConcurrentLinkedQueue<Task> allTasks;
    private static Context context;
    @Mock
    private static TransactionService service;

    @BeforeAll
    public static void initBeforeAll(){
        allTasks=new ConcurrentLinkedQueue<>();
        tasksForSeniorEmps=new ConcurrentLinkedQueue<>();
        tasksForEmps=new ConcurrentLinkedQueue<>();
        seniorEmps=new ConcurrentHashMap<>();
        emps=new ConcurrentHashMap<>();

    }
    @BeforeEach
    public void initBefore(){
        context=new Context(allTasks,tasksForEmps,tasksForSeniorEmps,emps,seniorEmps,service);
    }

    @Test
    public void test_getEmps(){
        ConcurrentHashMap<String,Processor> e=context.getEmps();
        assertEquals(e,emps);
    }
    @Test
    public void test_getSeniorEmps() {
        ConcurrentHashMap<String,Processor> se=context.getSeniorEmps();
        assertEquals(se,seniorEmps);
    }
    @Test
    public void test_getTasksForEmps() {
        ConcurrentLinkedQueue<Task> e=context.getTasksForEmps();
        assertEquals(e,tasksForEmps);
    }
    @Test
    public void test_getTasksForSeniorEmps() {
        ConcurrentLinkedQueue<Task> se=context.getTasksForSeniorEmps();
        assertEquals(se,tasksForSeniorEmps);
    }
    @Test
    public void test_getAllTasks() {
        ConcurrentLinkedQueue<Task> all=context.getAllTasks();
        assertEquals(all,allTasks);
    }

    @Test
    public void test_getService() {
        TransactionService s=context.getService();
        assertEquals(s,service);
    }
}
