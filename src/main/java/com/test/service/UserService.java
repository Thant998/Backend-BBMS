package com.test.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entity.User;
import com.test.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo repo;
	
	public void registerNupdate(User user) {
		repo.save(user);
	}
	
	public List<User> findbyEmail(String email){
		return repo.findByEmail(email);
	}

//	public List<User> findbyEmail(String email){
//		List<User> user=repo.findByEmail(email);
//		List<User> u1=new ArrayList<>();
//		for(User u:user)
//		{
//			User u2=new User();
//			u2.setId(u.getId());
//			u2.setName(u.getName());
//			u2.setEmail(u.getEmail());
//			u2.setPassword(u.getPassword());
//			u2.setConfirm(u.getConfirm());
//			u2.setRole(u.getRole());
//			u2.setEnable(u.isEnable());		
//			u1.add(u2);
//		}
//		
//		return u1;
//	}
	
}
