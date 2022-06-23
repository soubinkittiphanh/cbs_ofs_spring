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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ldb.core.api.JWTUtility;
import com.ldb.core.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)

            throws ServletException, IOException {

        String authorisation = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        logger.info("===>PATH: " + request.getServletPath());
        logger.info("Authenticating user");
        if (request.getServletPath().equals("/authenticate")) {
            filterChain.doFilter(request, response);
        } else {

            if (null != authorisation && authorisation.startsWith("Bearer ")) {
                token = authorisation.substring(7);
                userName = jwtUtility.getUserNameFromToken(token);
                logger.info("Token provided");
            }

            if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("Name provided");
                UserDetails userDetails = userService.loadUserByUsername(userName);
                if (jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }
            filterChain.doFilter(request, response);
        }

    }

}
