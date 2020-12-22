package com.naren.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .antMatcher("/**")
//	                .authorizeRequests()
//	                .antMatchers("/oauth/authorize**", "/login**", "/error**")
//	                .permitAll()
//	            .and()
//	                .authorizeRequests()
//	                .anyRequest().authenticated()
//	            .and()
//	                .formLogin().permitAll();
		 // Disable CSRF (cross site request forgery)
		    http.csrf().disable();
		    
		 // No session will be created or used by spring security
		    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 http.authorizeRequests()
		 .antMatchers("/login").permitAll()
		 .antMatchers("/oauth/authorize")
		 .authenticated()
//		 .and()
//		 .formLogin()
		 .and()
		 .requestMatchers()
		 .antMatchers("/login","/oauth/authorize");
	    }
	 
	 @Autowired
	    private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("siva").password(passwordEncoder.encode("secret"))
			.roles("USER");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow swagger to be accessed without authentication
	    web.ignoring().antMatchers("/v2/api-docs")//
	        .antMatchers("/swagger-resources/**")//
	        .antMatchers("/swagger-ui.html")//
	        .antMatchers("/configuration/**")//
	        .antMatchers("/webjars/**");
	}
	
//	@Bean("encoder")
//	@Primary
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
	
}