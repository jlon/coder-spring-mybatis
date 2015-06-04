package com.ailink.framework.aop.exception.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ailink.framework.exception.ExceptionUtil;
import com.ailink.framework.utils.email.MailSenderService;

/**
 * 异常处理
 * @author jlon
 *
 */
@Aspect
@Component
public class AopExceptionHandler {
	@Autowired
	private MailSenderService mailsenderService;
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* *..service.*Service.*(..))")
	public void aspect() {}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut ="aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		String aaString = ExceptionUtil.getExceptionAllinformation_01(ex);
		mailsenderService.sendMail("异常", aaString, "hellojlong@163.com");
	}
}
