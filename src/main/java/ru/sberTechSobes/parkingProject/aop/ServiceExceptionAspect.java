package ru.sberTechSobes.parkingProject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Aspect that converts service layer exceptions into {@link ResponseStatusException}
 * so that controllers can return appropriate HTTP responses.
 */
@Aspect
@Component
public class ServiceExceptionAspect {

    @Around("execution(* ru.sberTechSobes.parkingProject.service..*(..))")
    public Object translateExceptions(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервиса", ex);
        }
    }
}
