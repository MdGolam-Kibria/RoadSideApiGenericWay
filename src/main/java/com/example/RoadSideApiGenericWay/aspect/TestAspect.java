package com.example.RoadSideApiGenericWay.aspect;

import com.example.RoadSideApiGenericWay.dto.ProblemDto;
import com.example.RoadSideApiGenericWay.view.Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            logger.info(joinPoint.getTarget().getClass().getSimpleName());//for get for get class NAME
            logger.info("SIGNATURE = " + joinPoint.getSignature());
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

    @Before(value = "execution(public * com.example.RoadSideApiGenericWay.controller.ProblemController.*(..))" +
            "&& args(problemDto,bindingResult,request,response)")//for get method args
    public void getMethodData(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response, ProblemDto problemDto, BindingResult bindingResult) {
        logger.info("PROBLEM DESCRIPTION = " + problemDto.getDescription());
        logger.info("REQUEST URL" + request.getRequestURI());
        logger.info("USER_EMAIL" + request.getUserPrincipal().getName());//WAY TO GET USER NAME
        logger.info("USER_EMAIL way 2  = " + SecurityContextHolder.getContext().getAuthentication().getName());//WAY 2 TO GET USER NAME

    }

    @Around(value = "execution(public * com.example.RoadSideApiGenericWay.controller.ProblemController.*(..))" +
            "&& args(..)")
    public void TrackTime(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        final StopWatch stopWatch = new StopWatch();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {}", joinPoint, "\n" + "Method Execution Time = " + timeTaken);
    }

    @AfterReturning(value = "execution(public * com.example.RoadSideApiGenericWay.controller.ProblemController.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.info(String.valueOf(result));
    }
}
