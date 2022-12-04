package com.test.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.email.EmailConfig;
import com.test.email.MailRequest;
import com.test.entity.User;
import com.test.service.UserService;



@RestController
public class UserController {
	
	@Autowired
	EmailConfig mail;
	
	@Autowired
	UserService us;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	PasswordEncoder encode;
		
	
	// Log in
	@RequestMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public User login (Principal user) {
		List<User> users=us.findbyEmail(user.getName());
		if(users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	// Register
	@PostMapping("/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String  register(@RequestParam("user") String use,@RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException  {
		 User user  = new ObjectMapper().readValue(use, User.class);
	
		boolean valid = us.findbyEmail(user.getEmail()).stream().anyMatch(
				p -> p.getEmail().equals(user.getEmail()) 
				); 
		if(valid) {
			return "already";
		}
		if(!user.getConfirm().equals(user.getPassword())) {
			return "not match";
		}

				try {
					user.setPic(file.getBytes());
				} catch (IOException e) {
					System.out.println("third"+e);
					e.printStackTrace();
				}		

				user.setPicname(file.getOriginalFilename());
				user.setPassword(encode.encode(user.getPassword()));
				
//				MailRequest request = new MailRequest();
//				request.setTo(user.getEmail());
//				request.setSubject("To Confirm Email");
//				request.setName("mgmg");
//				request.setFrom("minnth696@gmail.com");
//				Map<String, Object> model = new HashMap<>();
//				model.put("Name", "http://localhost:8080/enable/"+user.getEmail());
//				model.put("location", "Bangalore,India");
//				 mail.sendEmail(request, model);
//				
				
					mail.simpleMail(user.getEmail(),"http://localhost:8080/enable/"+user.getEmail(),"for project");
					us.registerNupdate(user);
					
				return "success";
		}
	
	// enable account
	@RequestMapping("/enable/{email}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ModelAndView enable(@PathVariable String email) {
		List<User> users = us.findbyEmail(email);
		users.get(0).setEnable(true);
		us.registerNupdate(users.get(0));
		ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("success");
	      return modelAndView;
	}
	
	
	// forget Password < send email to change password>
	@PostMapping("/forgetpwd")
	@ResponseStatus(value = HttpStatus.OK)
	public String forgetPwd(@RequestBody String email,HttpServletRequest res) {
		boolean valid = us.findbyEmail(email).stream().anyMatch(
				p -> p.getEmail().equals(email) 
				);
		if(valid) {
			res.getServletContext().setAttribute("email", email);
			//mail.simpleMail(email,"http://localhost:4200/chnpwd","for project");
			return "success";
		}
		return "not found";
	}
	
	@PostMapping("/chnpwd")
	@ResponseStatus(value = HttpStatus.OK)
	public String changePwd(@RequestBody User user,HttpServletRequest res) {
		String email = (String) res.getServletContext().getAttribute("email");
		List<User> users = us.findbyEmail(email);
		users.get(0).setPassword(encode.encode(user.getPassword()));
		us.registerNupdate(users.get(0));
		res.getServletContext().removeAttribute("email");
		return "success";
	}
	
	
	// Update info
	@PostMapping("/update")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String update(@RequestParam("user") String use,@RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		 User user  = new ObjectMapper().readValue(use, User.class);
		try {
			user.setPic(file.getBytes());
		} catch (IOException e) {
			System.out.println("third"+e);
			e.printStackTrace();
		}		

	user.setPicname(file.getOriginalFilename());
	user.setPassword(encode.encode(user.getPassword()));
		us.registerNupdate(user);
	return "success";
	}

	//show profile
	@GetMapping("/showPf")
	@ResponseStatus(value = HttpStatus.OK)
	public User showPf(Authentication a) {
			return us.findbyEmail(a.getName()).get(0);
	}
	
	
	@GetMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
	    request.removeAttribute("Authorization");
	    
	}
	
	
	
	
	
}
