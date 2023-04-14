package com.safetrust.contact.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor extends WebRequestHandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger (this.getClass());

    public LoggingInterceptor(WebRequestInterceptor requestInterceptor) {
		super(requestInterceptor);
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		logger.info("Start request {} by IP {}", request.getServletPath(), request.getRemoteAddr());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception exception) {
    	logger.info("End request {} by IP {}", request.getServletPath(), request.getRemoteAddr());
    } 
}
