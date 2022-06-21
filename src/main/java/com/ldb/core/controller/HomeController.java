package com.ldb.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.core.api.JWTUtility;
import com.ldb.core.model.JWTRequest;
import com.ldb.core.model.JWTResponse;

@RestController
public class HomeController {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String greeting(){
        return "Welcome to API ";
    }
    @GetMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest)throws Exception{
        try {
            
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            //TODO: handle exception
            throw new Exception("INVALID_CREDENTIALS",e);
        }

        final UserDetails userDetails=userService.loadUserByUsername(jwtRequest.getUserName());
        final String token =jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }
}


