package com.test.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class GenerateToken extends OncePerRequestFilter   {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			SecretKey key = Keys.hmacShaKeyFor(SecurityContants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			String jwt = Jwts.builder().setIssuer("404").setSubject("jwt token")
						.claim("user",auth.getName())
						.claim("authorities",populateAuthorities(auth.getAuthorities()))
						.setIssuedAt(new Date())
						.setExpiration(new Date((new Date()).getTime()+86400000))
						.signWith(key).compact();
					response.setHeader(SecurityContants.JWT_HEADER,jwt);
					
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		if(request.getServletPath().equals("/login")) {
			return !request.getServletPath().equals("/login");
		}
	if(request.getServletPath().equals("/register")) {
			return !request.getServletPath().equals("/register");
		}
	if(request.getServletPath().equals("/enable/{email}")) {
		return !request.getServletPath().equals("/enable/{email}");
	}
	if(request.getServletPath().equals("/chnpwd")) {
		return !request.getServletPath().equals("/chnpwd");
	}
			return !request.getServletPath().equals("/forgetpwd");
		
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
        	authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
	}
	
}
