package com.example.demo.service.processing;

import com.example.demo.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface ProcessorService {
    public void processTask(Task task);
}
