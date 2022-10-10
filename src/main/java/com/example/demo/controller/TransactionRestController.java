package com.example.demo.controller;

import com.example.demo.service.processing.ProcessorService;
import com.example.demo.service.processing.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping(path="/api/points")
public class TransactionRestController {
    private ProcessorService service;
    public TransactionRestController(ProcessorService service){
        this.service=service;
    }
    @RequestMapping(value="/set",method = RequestMethod.POST)
    public ResponseEntity<String> createTransaction(@RequestBody DataExchange d){
        Pattern pattern= Pattern.compile("^[\\s]*([1-9]{1,1}|10)[\\s]*$");
        Matcher matcher=pattern.matcher(d.getPoint_amount());
        if(matcher.matches()){
            Task t=new Task(Integer.valueOf(d.getPoint_amount()));
            service.processTask(t);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Point amount must be a positive integer, >0 and <=10.\n");
    }

}
