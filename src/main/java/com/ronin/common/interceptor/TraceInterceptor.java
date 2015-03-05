/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.interceptor;


import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

/**
 *
 * @author esimsek
 */
public class TraceInterceptor extends CustomizableTraceInterceptor {

    private static final long serialVersionUID = 287162721460370957L;
    protected static Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);


    @Override
    protected void writeToLog(Log logger, String message, Throwable ex) {
        if (ex != null) {
            logger.debug(message, ex);
        } else {
            logger.debug(message);
        }
    }

    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return true;
    }
}
