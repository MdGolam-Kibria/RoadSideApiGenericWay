package com.example.RoadSideApiGenericWay.aspect;

import com.example.RoadSideApiGenericWay.view.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class TestAspect {
    Logger logger = LoggerFactory.getLogger(TestAspect.class);


    //    @Before("allControllerAndService()")
//    public void TestLog() {
//        logger.info("Aspect Called");
//    }
    @Around("allController()")
    public Response TestLog2(ProceedingJoinPoint joinPoint) {// "ProceedingJoinPoint" for get args(..) ,methodName,signature,class name etc
        Object[] signatures = joinPoint.getArgs();
        HttpServletRequest incomingRequest = null;
        for (int i = 0; i < signatures.length; i++) {
            if (signatures[i] instanceof HttpServletRequest) {
                incomingRequest = (HttpServletRequest) signatures[i];
                break;
            }
        }
        if (incomingRequest != null) {
            logger.info(incomingRequest.getRequestURI() + " IP ADDRESS" + incomingRequest.getRemoteAddr());
            logger.info(joinPoint.getSignature().getDeclaringTypeName());//for get packageName
            logger.info(joinPoint.getSignature().getName());//for get methodName
            logger.info(joinPoint.getTarget().getClass().getSimpleName());//for get for get class nane
            /*
            why here print "anonymousUser"
             */
            logger.info(SecurityContextHolder.getContext().getAuthentication().getName());//for get user Name
        }
        try {
            Response responce = (Response) joinPoint.proceed();
            logger.info("Aspect Call Ended");
            return responce;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

//    @Pointcut("(execution(public * com.example.RoadSideApiGenericWay.controller.*.*(..)) " +
//            "|| execution(public * com.example.RoadSideApiGenericWay.service.imple.*.*(..))) " +
//            "&& !execution(public * com.example.RoadSideApiGenericWay.service.imple.CustomUserDetailsService.*(..)) ) ")
//    public void allControllerAndService() {
//
//    }

    @Pointcut("(execution(public * com.example.RoadSideApiGenericWay.controller.*.*(..))&&args(..))" +
            "&& !execution(public * com.example.RoadSideApiGenericWay.controller.TestController.*(..))")
    public void allController() {

    }
}
