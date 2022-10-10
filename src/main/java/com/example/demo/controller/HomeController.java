package com.example.demo.controller;

import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import javax.validation.Valid;

@Slf4j
@Controller
public class HomeController {
    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private final TransactionService transactionService;
    public HomeController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @RequestMapping(value={"","/","home"})
    public String displayHome(Model model){
        model.addAttribute("transaction",new Transaction());
        return "home.html";
    }
    @RequestMapping(value="/saveMsg",method=POST)
    public String saveMessage(/*@RequestParam String point_amount*/ @Valid @ModelAttribute("transaction") Transaction t,Errors errors){
        //logger.info("POINT AMOUNT: "+ point_amount);
        if(errors.hasErrors()){
            logger.error("TRANSACTION FAILURE: "+ errors.toString());
            return "home.html";
        }
        transactionService.save(t);
        return "redirect:/";
    }
}
