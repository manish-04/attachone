package com.farzi;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@ComponentScan(basePackages = {"com.farzi","com.farzi.jobs"})
@EnableWebMvc
public class ApplicationConfig {

	public @Bean MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(new ServerAddress("ds133547.mlab.com", 33547), new ArrayList<MongoCredential>() {

            {
                add(MongoCredential.createCredential("manish04", "mchat", "manish@123".toCharArray()));
            }
        });
    }

	
	@Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongoClient(), "mchat");
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
    
    @Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		
		System.out.println("Multpart Resolver");
		
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}
    
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         
        //Using gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("graffitat.co@gmail.com");
        mailSender.setPassword("graffitat@123");
         
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
         
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
