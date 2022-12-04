package com.test.security;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class ValidToken extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(SecurityContants.JWT_HEADER);
		System.out.println("this is token " + jwt);
		if(jwt != null) {
//			try {
				SecretKey key = Keys.hmacShaKeyFor(SecurityContants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				Claims claims = Jwts.parserBuilder()
								.setSigningKey(key)
								.build()
								.parseClaimsJws(jwt)
								.getBody();
				String name = String.valueOf(claims.get("user"));
				String authorities = (String) claims.get("authorities");
				//response.setHeader(SecurityContants.JWT_HEADER,jwt);
				Authentication auth = new UsernamePasswordAuthenticationToken(name,null,
						AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				SecurityContextHolder.getContext().setAuthentication(auth);
		//	}
			//catch (Exception e) {
//				throw new BadCredentialsException("Invalid Token received!");
//			}
		}
		filterChain.doFilter(request, response);
	}
	
	
	 @Override protected boolean shouldNotFilter(HttpServletRequest request) {
		 if(request.getServletPath().equals("/login")) {
				return request.getServletPath().equals("/login");
			}
			if(request.getServletPath().equals("/register")) {
				return request.getServletPath().equals("/register");
		}
			if(request.getServletPath().equals("/enable/{email}")) {
				return request.getServletPath().equals("/enable/{email}");
		}
			if(request.getServletPath().equals("/chnpwd")) {
				return request.getServletPath().equals("/chnpwd");
		}
				return request.getServletPath().equals("/forgetpwd");
	
	 }
	
}
