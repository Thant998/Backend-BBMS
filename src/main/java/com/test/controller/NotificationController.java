package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.DTO.NotificationDTO;
import com.test.entity.User;
import com.test.repo.UserRepo;
import com.test.service.NotificationService;
import com.test.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class NotificationController 
{
	
	@Autowired
	UserRepo Ur;
	

	@Autowired
	UserService us;
	
	@Autowired
	NotificationService nS;


	@PostMapping(value = "/Noti", produces = "application/json")
	public void createNotification(@RequestBody NotificationDTO nfDto) 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		nfDto.setUserId(user.getId());
		 nS.NotificationInsert(nfDto);
//		 System.out.println("6666666"+cardDto.getCardId());		 
	}
	
	@GetMapping(value="/selectAllNoti")
	public List<NotificationDTO> selectAll()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		
		return nS.selectAll(user.getId());
	}
	
	@GetMapping(value="/deleteNoti/{nfId}")
	public void delete(@PathVariable int nfId)
	{
		nS.delete(nfId);
	}
	
}
