package com.test.security;

import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserAuthProvider provider;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
		cors().configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setMaxAge(3600L);
				return config;
			}
		}).and().csrf().disable()
				//.addFilterBefore(new FilterAngular(), BasicAuthenticationFilter.class)
				.addFilterBefore(new ValidToken(), BasicAuthenticationFilter.class)
				.addFilterAfter(new GenerateToken(), BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/register").permitAll()
				.antMatchers("/forgetpwd").permitAll()
				.antMatchers("/enable/{email}").permitAll()
				.antMatchers("/chnpwd").permitAll()
				.antMatchers("/api/v1/acceptInvite/{id}").permitAll()
				.antMatchers("/api/v1/download/{id}").permitAll()
				.antMatchers("/logout").permitAll()
	//			.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				
				.and().httpBasic();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
		
	}
	
	
	
}

