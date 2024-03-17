package com.epam.labtaskspringcore.global;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Component
public class LogFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.info("\n\n >> From LogFilter \nuri: " + uri + " \nmethod: " + method + "\n");

//        // Check with Siarhei if I need to log the body. I don't think so, because it can contain sensitive info but I
//        // need to check;
//        // I am also getting: Message: getReader() has already been called for this request
//        StringBuilder requestBody = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                requestBody.append(line);
//            }
//        }
//        log.info("Request body: " + requestBody.toString());

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        int code = response.getStatus();
        log.info("\n\n >> From LogFilter \nstatus code: " + code + "\n");
    }
}
