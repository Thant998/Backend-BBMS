package com.test.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.DTO.TaskDTO;
import com.test.DTO.TaskListDTO;
import com.test.repo.UserRepo;
import com.test.report.TaskReport;
import com.test.service.TaskService;
import net.sf.jasperreports.engine.JRException;



@RestController
@RequestMapping("/api/v1/")
public class TaskController 
{
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	TaskReport jp;
	
	@Autowired
	UserRepo Ur;
	
//	insert card
	@PostMapping(value = "/Task")
	public void createTask(@RequestBody TaskListDTO taskDto) 
	{
		 	taskService.createTask(taskDto);
	
	}
	
	
	//showTask
//	@GetMapping(value="/Task/{cardId}")
//	public ResponseEntity<List<TaskDTO>> showTask(@PathVariable String cardId) throws NumberFormatException, ParseException
//	{
//		CardDTO cardDto= new CardDTO();
//		cardDto.setCardId(cardId);
//		List<TaskDTO> card=taskService.showTask(cardDto);
//		return ResponseEntity.ok(card);
//	}
	
//	
//	//if Date added 
//	@PostMapping(value = "/Task/addDate/{boardId}")
//	public TaskDTO addDate(@PathVariable int boardId,@RequestBody TaskDTO taskDto) throws ParseException 
//	{
//			taskService.addDateTime(taskDto);
//			taskService.updateTaskForStatus(boardId,taskDto);
//		    return new TaskDTO();
//	}
//	
//	//update
	@PutMapping(value="/TaskUpdate/{taskId}")
	public void Update(@PathVariable String taskId,@RequestBody TaskDTO taskDto)
	{
		System.out.println(taskDto.getStartDate() +"time"+taskDto.getEndDate());
		taskService.updateTask(taskDto, taskId);
	}
	
	
	//showOneTask
	@GetMapping(value="/TaskShowOne/{taskId}")
	public TaskDTO ShowOne(@PathVariable String taskId)
	{
		System.out.println("aaaaaaaaaaaaaaaa"+taskId);
		TaskDTO dto=taskService.showOne(taskId);
		return dto;
	}
		
	
//	//delete
	@GetMapping(value="/Task/Delete/{taskId}")
	public void Delete (@PathVariable String taskId)
	{
		taskService.delete(taskId);
	}
	
//	
//	//dateUpdate
//	@PostMapping(value="TaskDateUpdate/{taskId}")
//	public void updateTaskDate(@PathVariable int taskId,@RequestBody TaskDTO taskDto) throws ParseException
//	{
//		taskService.updateDate(taskDto,taskId);
//	}
	
	
	
	@GetMapping("/sreport/{format}/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	  public String generateReport(@PathVariable("format") String format,@PathVariable("id") int id,HttpServletResponse reponse) throws JRException, IOException {
	    if(format.equals("pdf")) {
	       jp.exportPdfReport(format,id);  
	       return "pdf";
	    }
	     jp.exportExcelReport(reponse,id);
	    return "excel";
	  }
}
