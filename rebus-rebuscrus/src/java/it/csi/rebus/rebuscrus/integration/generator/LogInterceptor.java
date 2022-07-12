/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.generator;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogInterceptor {

	private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class);

	// esclude log metodo calcolaNumMessaggi() e viene richiamato in tutti i metodi
	// di business
	@Pointcut("execution(* it.csi.rebus.rebuscrus.business..*(..)) && !(execution(* it.csi.rebus.rebuscrus.business.service.impl.MessaggiServiceImpl.calcolaNumMessaggi(..)))")
	public void pointcut() {
	}

	// prima di eseguire il metodo
	@Before("pointcut()")
	public void logBeforeAllMethods(JoinPoint jp) throws IOException {
		LOGGER.info("START " + jp.getSignature().getName().toUpperCase()
				+ " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	// durante l'esecuzione del metodo
	@Around("pointcut()")
	public Object logMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// final StopWatch stopWatch = new StopWatch();
		ObjectMapper mapper = new ObjectMapper();
		Object[] array = pjp.getArgs();
		// stopWatch.start();
		// stopWatch.stop();
		Object object = pjp.proceed();

		try {
			LOGGER.info("******************** Classe " + methodSignature.getDeclaringType().getSimpleName() + ": "
					+ methodSignature.getName() + "() " + "parametri: " + mapper.writeValueAsString(array) + " Return: "
					+ mapper.writeValueAsString(object));
		} catch (Exception e) {
			LOGGER.error("Errore classe: " + methodSignature.getDeclaringType().getSimpleName() + ": "
					+ methodSignature.getName() + "(): ", e);
		}

		// LOGGER.info("Tempo esecuzione: " + stopWatch.getTotalTimeMillis());
		return object;

	}

	// cattura eccezioni del metodo appena eseguito
	@AfterThrowing(value = "pointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint pjp, Exception e) {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		LOGGER.error("Errore classe: " + methodSignature.getDeclaringType().getSimpleName() + ": "
				+ methodSignature.getName() + "(): ", e);
	}

	// dopo l'esecuzione del metodo
	@After("pointcut()")
	public void logAfterAllMethods(JoinPoint jp) throws IOException {
		LOGGER.info("END " + jp.getSignature().getName().toUpperCase()
				+ " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}