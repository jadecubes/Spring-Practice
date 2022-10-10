package com.example.demo.service;
import com.example.demo.dao.TransactionRepository;
import com.example.demo.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImpl implements TransactionService{
	private TransactionRepository transactionRepository;
	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository){this.transactionRepository=transactionRepository;}

	@Override
	public void save(Transaction t) {
		if(t==null) return;
		transactionRepository.save(t);
	}
	
}