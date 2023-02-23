package com.example.warehousetest.logging.activity.advice;

import com.example.warehousetest.logging.activity.model.ActivityLog;
import com.example.warehousetest.logging.activity.service.impl.ActivityLogServiceImpl;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;


@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ActivityLogServiceImpl activityLogServiceImpl;

    Object response;
    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(value="execution(* com.example.warehousetest.controller.*.*(..) )")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return response
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            response = joinPoint.proceed();
            if (log.isInfoEnabled()) {
                log.info("Exit: {}.{}() with response = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), response);
                saveLog(joinPoint, beginTime);
            }
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            saveLog(joinPoint, beginTime);

            throw e;
        }catch (Exception e) {
            response = e.getMessage();

            saveLog(joinPoint, beginTime);

            throw e;
        }
    }


    @Around("@annotation(com.example.warehousetest.logging.activity.annotation.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime=System.currentTimeMillis();
        Object obj=pjp.proceed();
        long endTime=System.currentTimeMillis();
        log.info("Method name"+pjp.getSignature()+" time taken to execute : "+(endTime-startTime));
        return obj;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ActivityLog activityLog = new ActivityLog();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
//        activityLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
//            activityLog.setParams(params);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            activityLog.setEndPoint(request.getServletPath());
            activityLog.setUsername("username");
            activityLog.setRequestTime(new Date());
            activityLog.setOperation(request.getMethod());
            activityLog.setActivities(request.getMethod());

//            String[] methodArray = activityLog.getMethod().split("\\.", 8);
            Gson gson = new Gson();

//            activityLog.setResponse(response != null ? gson.toJson(response) : null);
            activityLogServiceImpl.saveApplicationLog(activityLog);
        }
    }

}