package com.example.demo.unitTest.service.processing;

import com.example.demo.service.TransactionService;
import com.example.demo.service.processing.Context;
import com.example.demo.service.processing.Processor;
import com.example.demo.service.processing.Task;
import com.example.demo.service.processing.TaskDispatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.plaf.IconUIResource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskDispatcherUT {
    private static Context context;
    private static TaskDispatcher dispatcher;
    @Mock
    private static TransactionService service;
    @BeforeAll
    public static void initBeforeAll(){
        ConcurrentLinkedQueue<Task> allTasks=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> tasksForEmps=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> tasksForSeniorEmps=new ConcurrentLinkedQueue<>();
        ConcurrentHashMap<String, Processor> emps=new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Processor> seniorEmps=new ConcurrentHashMap<>();
        context=new Context(allTasks,tasksForEmps,tasksForSeniorEmps,emps,seniorEmps,service);
        dispatcher=new TaskDispatcher(context);
    }

    @Test
    public void test_dispatch(){
        Map<String, Processor> seniorEmp=new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<Task> allTasks=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> seniorTasks=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> generalTasks=new ConcurrentLinkedQueue<>();
        Map<String, Processor> generalEmp=new ConcurrentHashMap<>();
        Processor p=new Processor();
        p.initProcessor(context, Processor.Type.GENERAL);
        generalEmp.put("general",p);

        p=new Processor();
        p.initProcessor(context, Processor.Type.SENIOR);
        seniorEmp.put("senior",p);
        //add 2 tasks
        allTasks.add(new Task(7));
        allTasks.add(new Task(7));

        dispatcher.dispatch(generalEmp, allTasks,generalTasks);
        assertEquals(allTasks.size(),1);
        assertEquals(generalTasks.size(),1);

        dispatcher.dispatch(seniorEmp, allTasks,seniorTasks);
        assertEquals(allTasks.size(),0);
        assertEquals(seniorTasks.size(),1);

    }
}
