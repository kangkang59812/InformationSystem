package com.sm.global;

import com.sm.entity.Log;
import com.sm.entity.Staff;
import com.sm.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LogAdvice {
    @Autowired
    private LogService logService;
    @After("execution(* com.sm.controller.*.*(..)) && !execution(* com.sm.controller.SelfController.*(..)) && !execution(* com.sm.controller.*.to*(..)) && !execution(* com.sm.controller.LogController.*(..))")
    public void operationLog(JoinPoint joinPoint){
        Log log=new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName()); //类名
        log.setOperation(joinPoint.getSignature().getName()); //方法名
        HttpServletRequest request=(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session=request.getSession();
        Object obj=session.getAttribute("USER");
        Staff staff=(Staff)obj;
        log.setOperator(staff.getAccount());
        log.setResult("成功");
        logService.addOperationLog(log);
    }

    @AfterThrowing(throwing ="e" ,pointcut = "execution(* com.sm.controller.*.*(..)) && !execution(* com.sm.controller.SelfController.*(..))")
    public void systemLog(JoinPoint joinPoint,Throwable e){
        Log log=new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName()); //类名
        log.setOperation(joinPoint.getSignature().getName()); //方法名
        HttpServletRequest request=(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session=request.getSession();
        Object obj=session.getAttribute("USER");
        Staff staff=(Staff)obj;
        log.setOperator(staff.getAccount());
        log.setResult(e.getClass().getSimpleName());
        logService.addSystemLog(log);
    }

    @After("execution(* com.sm.controller.SelfController.login(..))")
    public void loginLog(JoinPoint joinPoint){
        Log log=new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName()); //类名 可以固定
        log.setOperation(joinPoint.getSignature().getName()); //方法名,可以固定
        HttpServletRequest request=(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session=request.getSession();
        Object obj=session.getAttribute("USER");

        //登录失败
        if(obj==null){
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        }
        else {
            Staff staff=(Staff)obj;
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }

        logService.addLoginLog(log);
    }

    @Before("execution(* com.sm.controller.SelfController.logout(..))")
    public void logoutLog(JoinPoint joinPoint){
        Log log=new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName()); //类名 可以固定
        log.setOperation(joinPoint.getSignature().getName()); //方法名,可以固定
        HttpServletRequest request=(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session=request.getSession();
        Object obj=session.getAttribute("USER");

        //登录失败
        if(obj==null){
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        }
        else {
            Staff staff=(Staff)obj;
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }

        logService.addLoginLog(log);
    }
}
