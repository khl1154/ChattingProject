package com.clone.chat.config.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapter extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter {


	@Value("${project.properties.username}")
	String username;

	@Value("${project.properties.password}")
	String password;


	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/h2-console",
				"/h2-console/**",
//				"/assets/**",
//				"/web-core-assets/**",
//				"/*.js",
//				"/*.map",
				"/favicon.ico"
		);
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "/apis","/apis/**", "/h2-console","/h2-console/**").permitAll()
//				.antMatchers("/", "/w","/apis/*").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails admin = User.builder()
				.username(username)
//				.password(password)
				.password(passwordEncoder.encode(password))
				.roles("SWAGGER").build();

		return new InMemoryUserDetailsManager(admin);
	}


	//	@Bean
//	public AuthenticationSuccessHandler authenticationSuccessHandler() {
//		return new AuthenticationSuccessHandler();
//	}
//
//	@Bean
//	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
//	}
//
//	@Bean
//	public AccessDeniedHandler accessDeniedHandler() {
//		return new AccessDeniedHandler();
//	}

}
