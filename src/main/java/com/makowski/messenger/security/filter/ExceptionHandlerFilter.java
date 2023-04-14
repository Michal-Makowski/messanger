package com.makowski.messenger.security.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.makowski.messenger.exception.EntityNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        try {
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            //User try make request with not valid JWT
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Java Web Token is not valid");
            response.getWriter().flush();
        } catch (EntityNotFoundException e) {
            //Wrong username by /login request
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Username doesn't exist");
            response.getWriter().flush();
        } catch (RuntimeException e) {
            //Wrong Json by /login request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Bad Request");
            response.getWriter().flush();
        }
    }

}
