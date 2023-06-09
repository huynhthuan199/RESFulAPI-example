package com.safetrust.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.safetrust.contact.middleware.LoggingInterceptor;

@Configuration
public class InterceptorAppConfig extends WebMvcConfigurationSupport {
	
   @Autowired
   LoggingInterceptor validateInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(validateInterceptor);
   }
   
}
