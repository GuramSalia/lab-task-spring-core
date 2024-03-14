package com.epam.labtaskspringcore.aspect;

import com.epam.labtaskspringcore.exception.InvalidRequestBodyException;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.utils.ControllerAuthentication;
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
    private final ControllerAuthentication controllerAuthentication;

    @Autowired
    public CheckUsernamePasswordAspect(ControllerAuthentication controllerAuthentication) {
        this.controllerAuthentication = controllerAuthentication;
    }

    //    @Before("execution(* com.epam.labtaskspringcore.controller.UserController.login(..))")
    @Before("@annotation(com.epam.labtaskspringcore.aspect.AuthenticateAspect)")
    public void checkUsernamePassword(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        boolean isUsernamePasswordInArgs = false;
        UsernamePassword usernamePassword;
        String username = null;
        String password = null;

        for (Object arg : args) {
            if (arg instanceof UsernamePassword) {
                isUsernamePasswordInArgs = true;
                usernamePassword = (UsernamePassword) arg;
                username = usernamePassword.getUsername();
                password = usernamePassword.getPassword();
            }
        }

        if (!isUsernamePasswordInArgs) {
            throw new InvalidRequestBodyException("Provide username and password");
        }

        log.warn("start authentication");

        controllerAuthentication.performAuthentication(username, password);
    }
}


