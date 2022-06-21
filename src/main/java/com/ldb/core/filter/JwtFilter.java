package com.ldb.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ldb.core.api.JWTUtility;
import com.ldb.core.controller.UserService;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtility jwtUtility;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String authorisation=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        if(null!=authorisation && authorisation.startsWith("Bearer ")){
            token =authorisation.substring(7);
            userName=jwtUtility.getUserNameFromToken(token);
        }
        if(null !=userName && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userService.loadUserByUsername(userName);
            if(jwtUtility.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);
        }
        
    }

    
    
}
