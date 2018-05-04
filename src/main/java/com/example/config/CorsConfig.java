package com.example.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

 @Configuration 
 public class CorsConfig extends WebMvcConfigurerAdapter{
		    @Override
		    public void addCorsMappings(CorsRegistry registry) {
		    	registry.addMapping("/**");
		           /*registry.addMapping("/api/**")
		           .allowedOrigins("http://192.168.1.97")
		           .allowedMethods("GET", "POST")
		           .allowCredentials(false).maxAge(3600);*/
		    }
}

