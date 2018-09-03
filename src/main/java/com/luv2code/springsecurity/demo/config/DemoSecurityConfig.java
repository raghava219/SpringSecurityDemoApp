package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to the security datasource
	
	@Autowired
	private DataSource securityDatasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*
		//add our users for an in memory authentication		
		UserBuilder users = User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
		.withUser(users.username("raghava").password("test123").roles("EMPLOYEE"))
		.withUser(users.username("raju").password("test123").roles("EMPLOYEE","MANAGER"))
		.withUser(users.username("rakesh").password("test123").roles("EMPLOYEE","ADMIN"));
		*/	

		// use jdbc authentication... 
		auth.jdbcAuthentication().dataSource(securityDatasource);
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		/*.anyRequest().authenticated()*/
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/leaders/**").hasRole("MANAGER")
		.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		.formLogin().loginPage("/showMyLoginPage")
		.loginProcessingUrl("/authenticateTheUser") // used in "fancy-login.jsp"
		.permitAll()//Allows everyone to see the login page.
		.and()
		.logout().permitAll() //Allows everyone to see the login page.
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}

	
	
	
}
