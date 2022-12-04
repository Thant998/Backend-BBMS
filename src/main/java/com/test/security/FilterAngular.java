package com.test.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;



public class FilterAngular implements Filter {

	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
	private Charset credentialsCharset = StandardCharsets.UTF_8;
	
	public static final String AUTHORIZATION = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader(AUTHORIZATION);
		System.out.println("tis is header ;; ;;"+header);
		if(header != null) {
				byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
				byte[] decoded;
				try {
					decoded = Base64.getDecoder().decode(base64Token);
				} catch (IllegalArgumentException e) {
					throw new BadCredentialsException(
							"Failed to decode basic authentication token");
				}
				String token = new String(decoded, getCredentialsCharset(req));
				int delim = token.indexOf(":");
				UsernamePasswordAuthenticationToken result  = new UsernamePasswordAuthenticationToken(token.substring(0, delim), token.substring(delim + 1));
				SecurityContextHolder.getContext().setAuthentication(result);			
		}
		chain.doFilter(request, response);
	}

	protected Charset getCredentialsCharset(HttpServletRequest request) {
		return getCredentialsCharset();
	}

	public Charset getCredentialsCharset() {
		return this.credentialsCharset;
	}
}
