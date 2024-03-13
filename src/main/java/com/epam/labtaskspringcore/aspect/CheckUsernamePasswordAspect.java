package com.epam.labtaskspringcore.aspect;

import com.epam.labtaskspringcore.exception.UnauthorizedException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class CheckUsernamePasswordAspect {
//    private final TraineeService traineeService;
//    private final TrainerService trainerService;
//
//    @Autowired
//    public CheckUsernamePasswordAspect(TraineeService traineeService, TrainerService trainerService) {
//        this.traineeService = traineeService;
//        this.trainerService = trainerService;
//    }
//
//    @Around("execution(* com.epam.labtaskspringcore.controller.UserController.login(..))")
//    public void authenticateLogin(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        Object[] args = joinPoint.getArgs();
//        boolean isUsernamePasswordInArgs = false;
//        UsernamePassword usernamePassword;
//        String username = null;
//        String password = null;
//
//        // Loop through the arguments to find UsernamePassword object
//        for (Object arg : args) {
//            if (arg instanceof UsernamePassword) {
//                isUsernamePasswordInArgs = true;
//                usernamePassword = (UsernamePassword) arg;
//                username = usernamePassword.getUsername();
//                password = usernamePassword.getPassword();
//            }
//        }
//
//        if (!isUsernamePasswordInArgs) {
//            throw new UnauthorizedException("Provide username and password");
//        }
//
//        Optional<Trainee> trainee = traineeService.findByUsernameAndPassword(username, password);
//        Optional<Trainer> trainer = trainerService.findByUsernameAndPassword(username, password);
//
//        if (trainee.isEmpty() && trainer.isEmpty()) {
//            throw new UnauthorizedException("Invalid username or password");
//        }
//
//        joinPoint.proceed(args);
//    }
}


