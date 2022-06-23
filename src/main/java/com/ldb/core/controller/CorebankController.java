package com.ldb.core.controller;

import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.core.api.T24Core;
import com.ldb.core.model.T24Request;
import com.ldb.core.model.T24Response;
import com.ldb.core.service.CorebankService;

@RestController
@RequestMapping(path = "/api/v1/core")
public class CorebankController {
    @Value("${corebank.version.passreset}")
    private String resetPasswordVersion;
    @Value("${corebank.uat.ip}")
    private String ipaddress;
    @Value("${corebank.uat.port}")
    private int port;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CorebankService corebankService;
    @PostMapping("/resetpassword")
    public T24Response resetT24UserPassword(@RequestBody T24Request t24Request) {
        // PASSWORD.RESET,RESET/I/PROCESS,
        String recordKey = String.valueOf(new Date().getTime());//"RESET.API3";
        String t24UserId=t24Request.getSignOnName();
        logger.info("USER: ===> "+t24Request.getSignOnName());
        logger.info("PASLEN: ===> "+t24Request.getPassLen());
        String otp =corebankService.generatePassword(t24Request.getPassLen()); //"autoGen1122";
        T24Core t24Core;
        try {
            logger.info("PORT UAT:" + this.port);
            logger.info("IP UAT:" + this.ipaddress);
            logger.info("PASS GEN:" + otp);
            Socket socket = new Socket(ipaddress, port);
            logger.info("Connect to T24 on port: " + port + " succeed");
            t24Core = new T24Core(socket, 60000);
            String ofsMessage = resetPasswordVersion + "DMUSER.13/123456," + recordKey
                    + ",USER.RESET:1="+t24UserId+",USER.TYPE:1=INT,USER.PASSWORD:1=" + otp;
            String respones = t24Core.postOfs(ofsMessage, 60000);
            logger.info("OFS: => "+ofsMessage);
            if (respones.contains(recordKey + "//1")) {
                logger.info(respones);
                return new T24Response("00", otp);
            } else {
                logger.error("Cannot reset password", "error" + respones);
                return new T24Response("05", respones);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("Cannot to T24 on port: " + port + " error: " + e);
            return new T24Response("05", e.toString());
        }


    }
}
