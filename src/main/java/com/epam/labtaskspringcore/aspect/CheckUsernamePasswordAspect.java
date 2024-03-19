package com.epam.labtaskspringcore.aspect;

import com.epam.labtaskspringcore.exception.InvalidRequestBodyException;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CheckUsernamePasswordAspect {
    private final UserService userService;

    @Autowired
    public CheckUsernamePasswordAspect(UserService userService) {
        this.userService = userService;
    }

    //    @Before("execution(* com.epam.labtaskspringcore.controller.UserController.login(..))")
    //    @Before("@annotation(com.epam.labtaskspringcore.aspect.CheckUsernamePassword)")
    @Before("@within(com.epam.labtaskspringcore.aspect.CheckUsernamePassword)")
    public void checkUsernamePassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        boolean hasUsernamePassword = false;
        UsernamePassword usernamePassword;
        String username = null;
        String password = null;

        for (Object arg : args) {
            if (arg instanceof UsernamePassword) {
                hasUsernamePassword = true;
                usernamePassword = (UsernamePassword) arg;
                username = usernamePassword.getUsername();
                password = usernamePassword.getPassword();

                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    hasUsernamePassword = false;
                }
                break;
            }
        }

        if (!hasUsernamePassword) {
            throw new InvalidRequestBodyException("Provide username and password");
        }

        log.info("start performing authentication with 'CheckUsernamePasswordAspect'");
        userService.performAuthentication(username, password);
    }
}


