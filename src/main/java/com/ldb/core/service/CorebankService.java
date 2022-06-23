package com.ldb.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CorebankService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    public String generatePassword(int passwordLength) {
        List<Integer> passwordGen = new ArrayList<>();
        int min = 0;
        int max = 9;
        String passwordGenStr="";
        for (int i = 0; i < passwordLength; i++) {
            int rnd=(int) (Math.random() * (max - min + 1) + min);
            passwordGen.add(rnd);
            passwordGenStr+=String.valueOf(rnd);
        }
        logger.info("String passgen: "+passwordGenStr);
        logger.info("Int passgen: "+passwordGen);
        return passwordGenStr;
    }
}
