	package com.ailink.framework.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.ailink.framework.aop.annotation.TestLog;

/**
 * * 切点类
 * 
 * @author jlon
 *
 */

@Aspect
@Component
public class SystemLogAspect {

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("@annotation(testLog)")
	public void doBefore(JoinPoint joinPoint,TestLog testLog) {
		try {
			getServiceMthodDescription(joinPoint);
			System.err.println(testLog.description());
			System.err.println(getServiceMthodDescription(joinPoint));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(TestLog.class)
							.description();
					break;
				}
			}
		}
		return description;
	}
}
