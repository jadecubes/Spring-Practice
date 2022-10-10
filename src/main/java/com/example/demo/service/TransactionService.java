package com.example.demo.service;
import com.example.demo.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface TransactionService{
	public void save(Transaction t);
}