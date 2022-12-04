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

import com.test.DTO.CommentDTO;
import com.test.entity.User;
import com.test.repo.UserRepo;
import com.test.service.CommentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserRepo uRepo;
	
	//save comment
	@PostMapping(value="/commentInsert")
	public void insert(@RequestBody CommentDTO commentDto)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(commentDto.getName()+" bnbnbnbbnbn");
		User user = uRepo.findByEmail(auth.getName()).get(0);
		commentDto.setUserId(user.getId());
		
		commentService.insertComment(commentDto);
		
	}	
	
	
	//showAllComment
	@GetMapping(value="/selectAllComment/{taskId}")
	public List<CommentDTO> selectAll(@PathVariable String taskId)
	{
		return commentService.selectAll(taskId);
	}
	
	
	@GetMapping(value="/delete/{commentId}")
	public void selectAll(@PathVariable int commentId)
	{
		commentService.Delete(commentId);
	}
	
}
