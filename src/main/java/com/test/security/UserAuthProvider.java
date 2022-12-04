package com.test.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.test.entity.User;
import com.test.repo.UserRepo;

@Component
public class UserAuthProvider implements AuthenticationProvider {

	@Autowired
	UserRepo repo;
	
	@Autowired
	PasswordEncoder encode;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		List<User> cust = repo.findByEmail(authentication.getName());
		if(cust.size() > 0) {
			if(encode.matches(authentication.getCredentials().toString(),cust.get(0).getPassword()) && cust.get(0).isEnable() == true) {
				List<GrantedAuthority> auth = new ArrayList<>();
				auth.add(new SimpleGrantedAuthority(cust.get(0).getRole()));
				 return new UsernamePasswordAuthenticationToken(cust.get(0).getEmail(),cust.get(0).getPassword(),auth);
			}else {
				throw new BadCredentialsException("Invalid password!");
			}
		}else {
			throw new BadCredentialsException("No user registered with this details!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
