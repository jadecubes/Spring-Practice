package com.example.demo.unitTest.service;

import com.example.demo.dao.TransactionRepository;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import com.example.demo.service.TransactionServiceImpl;
import com.example.demo.service.processing.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionServiceUT {
    @Mock
    private TransactionRepository repository;
    private Transaction ts;
    private Transaction result;
    @Test
    public void test_save(){
        TransactionService service=new TransactionServiceImpl(repository);

        service.save(null);
        when(repository.save(Mockito.any(Transaction.class)))
                .thenAnswer(i -> {
                    assertEquals ( i.getArguments()[0],ts);
                    return i.getArguments()[0];
                });
        Task task=new Task(3);

        ts=new Transaction();
        task.setProcessor_name("Java");
        ts.setValues(task);
        service.save(ts);
        when(repository.save(Mockito.any(Transaction.class)))
                .thenAnswer(i -> {
                    assertEquals ( i.getArguments()[0],ts);
                    return i.getArguments()[0];
                });

    }
}
