package com.example.demo.unitTest.service.processing;

import com.example.demo.service.processing.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskUT {
    private static Task task;
    private static int pNum=3;
    @BeforeAll
    public static void initBeforeAll() {
        task=new Task(pNum);
    }

    @Test
    public void test_getPoint_amount(){
        int num=task.getPoint_amount();
        assertTrue(num>=1 && num<=10);
    }
    @Test
    public void test_getTransaction_id(){
        String id=task.getTransaction_id();
        assertTrue(id.length()==32);
    }
    @Test
    public void test_getBusiness_time(){
        Date date=task.getBusiness_time();
        Date now=new Date();
        assertTrue(now.after(date));
    }
    @Test
    public void test_getProcessor_name(){
        String name=task.getProcessor_name();
        assertNotNull(name);
    }
    @Test
    public void test_setProcessor_name(){
        String name="Java";
        task.setProcessor_name("Java");
        assertEquals(name,task.getProcessor_name());
    }
    @Test
    public void test_getProcess_cost(){
        int cost=task.getProcess_cost();
        assertTrue(cost>=300 && cost<=5000);
    }
}
