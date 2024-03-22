package com.epam.labtaskspringcore.global;

import com.epam.labtaskspringcore.controller.TraineeController;
import com.epam.labtaskspringcore.controller.TrainerController;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
public class LogFilter implements Filter {

    private final PrometheusCounter counter;

    @Autowired
    public LogFilter(PrometheusCounter counter) {
        this.counter = counter;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.info("\n\n >> From LogFilter >> uri: " + uri + "\n");
        log.info("\n\n >> From LogFilter >> method: " + method + "\n");

        boolean isGetTraineeEndpoint = uri.equals("/trainee-get");
        boolean isGetTrainerEndpoint = uri.equals("/trainer-get");

        //Request Parameters:
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.isEmpty()) {
            log.info("\n\n >> From LogFilter >> No request parameters\n");
        }
        for (String parameter : parameters.keySet()) {
            String[] values = parameters.get(parameter);
            log.info(">> From LogFilter >> Parameter: " + parameter + ", Value(s): " + Arrays.toString(values));
        }

        //https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        int code = response.getStatus();
        log.info("\n\n >> From LogFilter >> status code: " + code + "\n");

        if (isGetTraineeEndpoint && code == 200) {
            log.info("\n\n >> From LogFilter >> counter trainee-get: positive++ \n");
            counter.getHitGetTraineeCounter().increment();
        } else if (isGetTraineeEndpoint && code != 200) {
            log.info("\n\n >> From LogFilter >> counter trainee-get: negative++ \n");
            counter.getNotHitGetTraineeCounter().increment();
        }

        if (isGetTrainerEndpoint && code == 200) {
            log.info("\n\n >> From LogFilter >> counter trainer-get: positive++ \n");
            counter.getHitGetTrainerCounter().increment();
        } else if (isGetTrainerEndpoint && code != 200) {
            log.info("\n\n >> From LogFilter >> counter trainer-get: negative++ \n");
            counter.getNotHitGetTrainerCounter().increment();
        }

    }
}
