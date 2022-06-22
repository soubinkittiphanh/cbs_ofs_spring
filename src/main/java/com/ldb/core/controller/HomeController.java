package com.ldb.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.core.api.JWTUtility;
import com.ldb.core.model.JWTRequest;
import com.ldb.core.model.JWTResponse;
import com.ldb.core.service.UserService;


@RestController
public class HomeController {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greeting() {
        return "Welcome to API ";
    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {
        // logger.info("Authenticating user");
        System.out.println("USER: " + jwtRequest.getUserName());
        System.out.println("Password: " + jwtRequest.getPassword());
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // TODO: handle exception
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtility.generateToken(userDetails);
        System.out.println("TOKEN: "+token);
        return new JWTResponse(token);
    }
}
