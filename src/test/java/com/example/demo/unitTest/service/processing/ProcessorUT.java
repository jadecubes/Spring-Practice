package com.example.demo.unitTest.service.processing;

import com.example.demo.service.TransactionService;
import com.example.demo.service.TransactionServiceImpl;
import com.example.demo.service.processing.Context;
import com.example.demo.service.processing.Processor;
import com.example.demo.service.processing.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProcessorUT {
    private static Processor processor;
    private static Context context;
    @Mock
    private static TransactionService service;
    @BeforeAll
    public static void initBeforeAll(){
        processor=new Processor();
        ConcurrentLinkedQueue<Task> allTasks=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> tasksForEmps=new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Task> tasksForSeniorEmps=new ConcurrentLinkedQueue<>();
        ConcurrentHashMap<String, Processor> emps=new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Processor> seniorEmps=new ConcurrentHashMap<>();
        context=new Context(allTasks,tasksForEmps,tasksForSeniorEmps,emps,seniorEmps,service);
    }
    @BeforeEach
    public void initBeforeEach() {
        processor.initProcessor(context, Processor.Type.GENERAL);
    }

    @Test
    public void test_getState(){
        Processor.ProcessorState state=processor.getProcessorState();
        assertEquals(state, Processor.ProcessorState.IDLE);
    }

    @Test
    public void test_setState(){
        Processor.ProcessorState pre=processor.getProcessorState();
        //get next state
        int curIdx=pre.ordinal();
        Processor.ProcessorState[] stateAry= Processor.ProcessorState.values();
        int nxtIdx=(curIdx+1)%stateAry.length;

        processor.setState(stateAry[nxtIdx]);
        assertNotEquals(processor.getProcessorState(),pre);
    }
    @Test
    public void test_getContext(){
        Context context=processor.getContext();
        assertNotNull(context);
    }
    @Test
    public void test_process(){
        ConcurrentLinkedQueue<Task> que=new ConcurrentLinkedQueue<>();
        que.add(new Task(4));
        processor.setState(Processor.ProcessorState.SCHEDULED_TO_PROCCESS);
        processor.process(que);
        if(service==null)
            assertEquals(que.size(),1);
        else
            assertEquals(que.size(),0);
    }
    @Test
    public void test_initProcessor(){
        Context oContext=processor.getContext();
        Context context=new Context(null,null,null,null, null,null);
        Processor.Type oType= processor.getType();
        //get next type
        int curIdx=oType.ordinal();
        Processor.Type[] typeAry=Processor.Type.values();
        int nxtIdx=(curIdx+1)%typeAry.length;
        Processor.Type type=typeAry[nxtIdx];

        processor.initProcessor(context, type);
        assertNotEquals(oContext,context);
        assertNotEquals(oType,type);
    }
    @Test
    public void test_isOccupied(){
        Processor.ProcessorState[] stateAry= Processor.ProcessorState.values();
        for(int i=0;i<stateAry.length;i++){
            Processor.ProcessorState s=stateAry[i];
            processor.setState(s);
            if(s== Processor.ProcessorState.SCHEDULED_TO_PROCCESS ||
               s== Processor.ProcessorState.IS_PROCESSING)
                assertEquals(processor.isOccupied(),true);
            else
                assertEquals(processor.isOccupied(),false);
        }
    }


    @Test
    public void test_sleepTime(){
        int amount=10;
        Task t=new Task(amount);
        Context oContext=processor.getContext();
        processor.initProcessor(oContext, Processor.Type.GENERAL);
        int time=processor.sleepTime(t);
        assertEquals(time,t.getProcess_cost());

        processor.initProcessor(oContext, Processor.Type.SENIOR);
        time=processor.sleepTime(t);
        assertEquals(time,0);
    }
}
