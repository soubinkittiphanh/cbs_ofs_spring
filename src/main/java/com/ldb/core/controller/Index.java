package com.ldb.core.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.core.service.EmailService;

@RestController
@RequestMapping(path = "/api/v1")
public class Index {
    @Autowired
    private EmailService emailService;
    @Value("${t24_hours}")
    private String appTitle;
    private Logger logger=LoggerFactory.getLogger(Index.class);
    @GetMapping("/")
    public String greetingSpring(){
        logger.info("Request involked at home path /");
        return "Hello you are the best "+appTitle;
    }
    @PostMapping("/sendmail")
    public String sendMail(){
        logger.info("Send mail request");
        // emailService.sendEmail();
        try {
            emailService.mail_new("soubin@ldblao.la", "billboyslm@gmail.com", "Subject", "Message");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error("Error", e);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error("Error", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error("Error", e);
        }
        return "Mail has sent i hope";
    }
}
