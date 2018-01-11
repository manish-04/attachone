package com.farzi;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {
    
	public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context
          = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.farzi");
 
        container.addListener(new ContextLoaderListener(context));
 
        ServletRegistration.Dynamic dispatcher = container
          .addServlet("dispatcher", new DispatcherServlet(context));
         
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
    
	
    
}