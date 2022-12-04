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

import com.test.DTO.CardDTO;
import com.test.DTO.TaskDTO;
import com.test.DTO.UserTaskDTO;
import com.test.DTO.UserTaskList;
import com.test.entity.User;
import com.test.repo.UserRepo;
import com.test.service.UserTaskService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")


public class UserTaskController {
	@Autowired 
	UserTaskService userTaskService;
	
	@Autowired
	UserRepo Ur;
	
	@PostMapping(value="/inviteMember")
	public void inserMember (@RequestBody UserTaskDTO dto)
	{
		userTaskService.insertUser(dto);
	}
	
	@GetMapping(value="/ShowTaskMember/{taskId}")
	public UserTaskList showTaskMember(@PathVariable String taskId)
	{
		System.out.println("Task Id for pop up"+taskId);
		return userTaskService.showTaskMember(taskId);
	}
	
	//for TaskShow for current user
	@GetMapping(value="/Task/{cardId}")
	public List<TaskDTO> showTaskMember1(@PathVariable String cardId)
	{

		CardDTO cardDto= new CardDTO();
		cardDto.setCardId(cardId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		return userTaskService.showUsers(cardDto,user.getId());
	}
}
