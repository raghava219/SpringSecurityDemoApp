package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;
import javax.sql.DataSource;

@Configuration
@EnableWebMvc// This gives similar functionality of <mvc:annotation-driven>
@ComponentScan(basePackages="com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {

	// set up variable to hold the properties
	@Autowired
	private Environment env; // Spring helper to hold the properties

	// set up a logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName()); 
	
	//define a bean for ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	//define a bean for our security datasource
	@Bean
	public DataSource securityDatasource(){
		
		// create connection pool
		ComboPooledDataSource  securityDatasource = new ComboPooledDataSource();
		
		// set the jdbc driver class
		try{
			securityDatasource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch(PropertyVetoException exc){
			throw new RuntimeException(exc); // Making the system known if something goes wrong
		}
		// log the connection props
		logger.info(">>>jdbc.url  :"+env.getProperty("jdbc.url"));
		logger.info(">>>jdbc.user :"+env.getProperty("jdbc.user"));
		
		// set database connection props
		securityDatasource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDatasource.setUser(env.getProperty("jdbc.user"));
		securityDatasource.setPassword(env.getProperty("jdbc.password"));
		
		// set connection pool
		securityDatasource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDatasource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDatasource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDatasource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		
		return securityDatasource;
	}
	
	// need a helper method
	
	private int getIntProperty(String propName){
		String propVal = env.getProperty(propName);
		return Integer.parseInt(propVal);
	}
	
	
}














