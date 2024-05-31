package cg.codegym.minitest.springboot2.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logger {
    @AfterThrowing(pointcut = "execution(public * cg.codegym.minitest.springboot2.Repository.IComputerRepository.findAll(..))",
            throwing = "e")
    public void logMethod(Exception e){
        System.out.println("[CMS] co loi xay ra: " + e.getMessage());
    }
    @AfterThrowing(
            pointcut = "execution(public * cg.codegym.minitest.springboot2.Repository.IComputerRepository.*(..))",
            throwing = "e"
    )
    public void logClass(JoinPoint joinPoint, Exception e) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.printf("[CMS] có lỗi xảy ra:  %s.%s%s: %s%n", className, method, args, e.getMessage());
    }
}
