package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.DTO.ActivityDTO;
import com.test.DTO.ActivityListDTO;
import com.test.service.ActivityService;
import com.test.service.TaskService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ActivityController 
{
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TaskService taskService;
	
	//save
	@PostMapping(value="/Activity")
	public void InsertActivity(@RequestBody ActivityListDTO dto)
	{
		activityService.insertActivity(dto);
		taskService.CardIdChange(dto);
	}
	
	
//	//update
//	@PutMapping(value="/Activity/{activityId}")
//	public void updateActivity(@PathVariable int activityId,@RequestBody ActivityDTO dto)
//	{
//		activityService.updateActivity(activityId, dto);
//	}
//	
	
	//ShowAll
	@GetMapping(value="/ActivityShowAll/{taskId}")
	public ResponseEntity<List<ActivityDTO>> showAllActivity(@PathVariable String  taskId)
	{
		ActivityDTO dto = new ActivityDTO();
		dto.setTaskId(taskId);
		
		List<ActivityDTO>list= activityService.showAll(dto);
		return ResponseEntity.ok(list);
	}
	
	
	//ShowOne
	@GetMapping(value="/ActivityShow/{activityId}")
	public ResponseEntity<ActivityDTO> showOneActivity(@PathVariable int activityId)
	{
		ActivityDTO dto=activityService.showOne(activityId);
		return ResponseEntity.ok(dto);
	}
	
	
	//delete
	@GetMapping(value="/Delete/{activityId}")
	public void Delete(@PathVariable int activityId)
	{
		activityService.Delete(activityId);
	}

}
