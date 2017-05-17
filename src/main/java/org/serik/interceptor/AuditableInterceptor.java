package org.serik.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

@Auditable
@Interceptor
public class AuditableInterceptor {

    @Inject
    private Logger logger;

    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
	long begin = System.currentTimeMillis();
	try {
	    return ic.proceed();
	} finally {
	    logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< AUDITABLE OUTPUT BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	    logger.info("Method " + ic.getMethod().getName() + " in class " + ic.getTarget().getClass().getSimpleName()
		    + " executed in " + (System.currentTimeMillis() - begin) + " millisecs");
	    logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< AUDITABLE OUTPUT END >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
    }
}
