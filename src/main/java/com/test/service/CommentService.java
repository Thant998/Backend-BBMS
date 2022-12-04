package com.test.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.CommentDTO;
import com.test.entity.Comment;
import com.test.entity.User;
import com.test.repo.CommentRepository;
import com.test.repo.TaskRepository;
import com.test.repo.UserRepo;

@Service
public class CommentService {
	
	@Autowired
	CommentRepository commentRepo;
	
	
	@Autowired
	UserRepo userR;

	@Autowired
	TaskRepository taskR;
	
	public void insertComment(CommentDTO commentDto)
	{
		
		Comment cmt = new Comment();
		//cmt.setId(commentDto.getId());
		cmt.setName(commentDto.getName());
		cmt.setUserName(commentDto.getUserName());
		
		cmt.setUser(userR.memberQuery1(commentDto.getUserId()));
		cmt.setTask(taskR.findByIdAndDeleteStatus(commentDto.getTaskId(), false));
		LocalDate date  = LocalDate.now();
		LocalTime time = LocalTime.now();
		cmt.setDate(date.toString());
		cmt.setTime(time.toString());
		commentRepo.save(cmt);
		
	}
	
//	public void updateComment(CommentDTO commentDto , int commentId)
//	{
//		
//		Comment cmt = commentRepo.findByIdAndDeleteStatus(commentId , false);
//		cmt.setId(commentId);
//		cmt.setUserName(commentDto.getUserName()+" "+"Edited");
//		cmt.setName(commentDto.getName());
//		cmt.setActivity(activityRepo.findByIdAndDeleteStatus(commentDto.getActivityId(),false));
//		//LocalDate date  = LocalDate.now();
//		//LocalTime time = LocalTime.now();
//		//cmt.setDate(date.toString());
//		//cmt.setTime(time.toString());
//		commentRepo.save(cmt);	
//	}
	
	public List<CommentDTO> selectAll(String taskId)
	{
		List<Comment> list = commentRepo.selectAllComment(taskId);
		List<CommentDTO> dto = new ArrayList<>();
		for (Comment c : list)
		{
			CommentDTO commentObj = new CommentDTO();
			commentObj.setTaskId(taskId);
			commentObj.setDate(c.getDate());
			commentObj.setId(c.getId());
			commentObj.setName(c.getName());
			commentObj.setTime(c.getTime());
			User user = userR.findById(c.getUser().getId()).get();
			commentObj.setUserId(user.getId());
			commentObj.setUserName(user.getName());
			dto.add(commentObj);
		}
		
		return dto;
	}
	
	public void Delete(int cmtId)
	{
		Comment cmt=commentRepo.findByIdAndDeleteStatus(cmtId, false);
		cmt.setDeleteStatus(true);
		commentRepo.save(cmt);
	}
	
}
