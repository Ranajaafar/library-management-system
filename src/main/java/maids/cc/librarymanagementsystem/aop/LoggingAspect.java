package maids.cc.librarymanagementsystem.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import static java.time.Instant.now;

@Aspect
public class LoggingAspect {

    @Pointcut(
        "within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
        //method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut(
        "within(maids.cc.librarymanagementsystem.auth..*)" +
        " || within(maids.cc.librarymanagementsystem.patron..*)" +
        " || within(maids.cc.librarymanagementsystem.book..*)" +
        " || within(maids.cc.librarymanagementsystem.borrowingrecord..*)"
    )
    public void applicationPackagePointcut() {
        //method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    //log after throwing an exception in methods specified by the pointcuts
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger(joinPoint).error(
                "Exception in {}() with cause = '{}' and exception = '{}'",
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e
        );
    }

    //log entrance and exit of specified methods with their arguments if debug logging is enabled
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        Instant startInstant = now();
        log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            log.info("Exit: {}() with result = {}, execution ended in {}ms", joinPoint.getSignature().getName(), result, Duration.between(startInstant, Instant.now()).toMillis());
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}(), execution ended in {}ms", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName(), Duration.between(startInstant, Instant.now()).toMillis());
            throw e;
        }
    }
}
