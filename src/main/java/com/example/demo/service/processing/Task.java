package com.example.demo.service.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task {
    private Integer point_amount;
    private String transaction_id;
    private Date business_time;
    private Integer process_cost;

     public Task(Integer point_amount){
         this.point_amount=point_amount;
         this.transaction_id= UUID.randomUUID().toString().replace("-", "");
         this.process_cost=point_amount*getRandomNumberUsingNextInt(300,500);
         this.business_time=new Date();
     }
    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public Integer getPoint_amount() {
        return point_amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public Date getBusiness_time() {
        return business_time;
    }

    public Integer getProcess_cost() {
        return process_cost;
    }
}
