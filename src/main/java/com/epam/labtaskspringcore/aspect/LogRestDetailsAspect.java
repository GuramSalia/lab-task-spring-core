package com.epam.labtaskspringcore.aspect;

import com.epam.labtaskspringcore.controller.TraineeController;
import com.epam.labtaskspringcore.controller.TrainerController;
import com.epam.labtaskspringcore.global.PrometheusCounter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@Order(2)
public class LogRestDetailsAspect {
    private final PrometheusCounter counter;


    @Autowired
    public LogRestDetailsAspect(TraineeController traineeController, TrainerController trainerController, PrometheusCounter counter) {
        this.counter = counter;
    }

    @Pointcut("@within(com.epam.labtaskspringcore.aspect.LogRestDetails)")
    public void logRestDetailsPointcut() {
    }

    @AfterReturning(pointcut = "logRestDetailsPointcut()", returning = "response")
    public void logEndpointAndRequestDetails(JoinPoint joinPoint, Object response) {

        String endpointPath = getEndpointPath(joinPoint);

        boolean isGetTrainerEndpoint = endpointPath
                .equals("com.epam.labtaskspringcore.controller.TrainerController.getTrainer");

        boolean isGetTraineeEndpoint = endpointPath
                .equals("com.epam.labtaskspringcore.controller.TraineeController.getTrainee");

        log.info("\n\n-\tREST endpoint: {}\n", endpointPath);

        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = getRequest(args);
        if (request != null) {
            String requestMethod = request.getMethod();
            //            String signatureShortString = joinPoint.getSignature().toShortString();
            log.info("\n\n-\tRequest method: {}\n", requestMethod);
            log.info("\n\n-\tRequest URI: {}\n", request.getRequestURI());
            log.info("\n\n-\tRequest parameters: {}\n", Arrays.toString(args));
        }

        if (response instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            Object responseBody = responseEntity.getBody();

            log.info("\n\n-\tResponse Code: {}\n", statusCode.value());
            log.info("\n\n-\tResponse Body: {}\n", responseBody);

            if (isGetTrainerEndpoint && statusCode.value() == 200) {
                counter.getSuccessfulGetTrainerHitCounter().increment();
            } else {
                counter.getUnsuccessfulGetTrainerHitCounter().increment();
            }

            if (isGetTraineeEndpoint && statusCode.value() == 200) {
                counter.getSuccessfulGetTraineeHitCounter().increment();
            } else {
                counter.getUnsuccessfulGetTraineeHitCounter().increment();
            }
        }
    }

    private String getEndpointPath(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }

    private HttpServletRequest getRequest(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return (HttpServletRequest) arg;
            }
        }
        return null;
    }
}
