package com.test.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.test.DTO.ActivityListDTO;
import com.test.DTO.CardDTO;
import com.test.DTO.TaskDTO;
import com.test.DTO.TaskListDTO;
import com.test.entity.Card;
import com.test.entity.Task;
import com.test.entity.User;
import com.test.repo.CardRepository;
import com.test.repo.TaskRepository;
import com.test.repo.UserRepo;

@Service

public class TaskService 
{
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	UserRepo Ur;
	
	@Autowired
	private UserTaskService utS;
	
	//task update
	public void updateTask(TaskDTO taskDto,String taskId)
	{
		System.out.println(taskDto.getStartDate() +"time"+taskDto.getEndDate());
		Task task=taskRepository.findByIdAndDeleteStatus(taskId, false);
		task.setName(taskDto.getName());
		task.setCard(cardRepository.findByIdAndDeleteStatus(taskDto.getCardId(), false));
		task.setStartDate(taskDto.getStartDate());
		task.setEndDate(taskDto.getEndDate());
		task.setStartTime(taskDto.getStartTime());
		task.setEndTime(taskDto.getEndTime());
		task.setDescription(taskDto.getDescription());
		taskRepository.save(task);
	}
	
	public void createTask(TaskListDTO taskDto)
	{
		List<TaskDTO> list = taskDto.getList();
		for (TaskDTO t:list)
		{
			Task task1=taskRepository.findByIdAndDeleteStatus(t.getId(), false);
			if (task1==null) 
			{
				Task task = new Task();
				Date date = new Date();
				long msec = date.getTime();
				task.setId(String.valueOf(msec));
				task.setName(t.getName());
				task.setCard(cardRepository.findByIdAndDeleteStatus(t.getCardId(), false));
				task.setStartDate(t.getStartDate());
				task.setEndDate(t.getEndDate());
				task.setStartTime(t.getStartTime());
				task.setEndTime(t.getEndTime());
				task.setDescription(t.getDescription());
				taskRepository.save(task);
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				System.out.println(auth.getName());
				User user = Ur.findByEmail(auth.getName()).get(0);
			 	utS.privateUser(user.getId(),task.getId());
		
			}
			else 
			{
				Task task=taskRepository.findByIdAndDeleteStatus(t.getId(), false);
				task.setId(t.getId());
				task.setName(t.getName());
				task.setCard(cardRepository.findByIdAndDeleteStatus(t.getCardId(), false));
//				task.setStartDate(t.getStartDate());
//				task.setEndDate(t.getEndDate());
//				task.setStartTime(t.getStartTime());
//				task.setEndTime(t.getEndTime());
				task.setDescription(t.getDescription());
				taskRepository.save(task);
				
//				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//				System.out.println(auth.getName());
//				User user = Ur.findByEmail(auth.getName()).get(0);
//			 	utS.privateUser(user.getId(),task.getId());
			}		
		}
	}
	
	//after Activity add cardId Change
	public void CardIdChange(ActivityListDTO listDto)
	{
		
//		for(ActivityDTO ALD : listDto.getList())
//		{
			
			Task task=taskRepository.findByIdAndDeleteStatus(listDto.getTaskId(), false);
			task.setId(listDto.getTaskId());
			Card card=cardRepository.findByIdAndDeleteStatus(listDto.getCardId(), false);
			task.setCard(card);
			taskRepository.save(task);
//		}
	}
	
	//task Create
//	public void createTask(TaskDTO taskDto)
//	{
//		List<Task> task= taskRepository.findByIdAndDeleteStatus1();
//		for (Task t : task)
//		{
//			if ( taskDto.getId().equals(t.getId()) )
//			{
//				Task taskObj = taskRepository.findByIdAndDeleteStatus(taskDto.getId(), false);
//				taskObj.setCard(cardRepository.findByIdAndDeleteStatus(taskDto.getCardId(), false));
//				taskObj.setId(taskDto.getId());
//				taskObj.setName(taskDto.getName());
//				taskObj.setDescription(taskDto.getDescription());
//				taskObj.setStartDate(taskDto.getStartDate());
//				taskObj.setEndDate(taskDto.getEndDate());
//				taskObj.setStartTime(taskDto.getStartTime());
//				taskObj.setEndTime(taskDto.getEndTime());
//				System.out.println("update task"+taskDto.getId());
//				taskRepository.save(taskObj);
//			}
//			else 
//			{
//				Task taskObj2 = new Task();
//				Date date = new Date();
//				long msec = date.getTime();
//				taskObj2.setId(String.valueOf(msec));
//				taskObj2.setName(taskDto.getName());
//				taskObj2.setDescription(taskDto.getDescription());
//				taskObj2.setStartDate(taskDto.getStartDate());
//				taskObj2.setEndDate(taskDto.getEndDate());
//				taskObj2.setStartTime(taskDto.getStartTime());
//				taskObj2.setEndTime(taskDto.getEndTime());
//				taskObj2.setCard(cardRepository.findByIdAndDeleteStatus(taskDto.getCardId(), false));
//				System.out.println("insert task"+taskDto.getId());
//				taskRepository.save(taskObj2);
//			}
//		}
////		//task.setId(taskDto.getId());
////		task.setName(taskDto.getName());
////		task.setCard(cardRepository.findById(taskDto.getCardId()).get());
////		task.setStartDate(taskDto.getStartDate());
////		task.setEndDate(taskDto.getEndDate());
////		taskRepository.save(task);
//		
//	}
	
	//Date time change
	public static String englishTime(String input) throws ParseException 
	{
		// Format of the date defined in the input String
	    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

	    // Change the pattern into 24 hour format
	    DateFormat format = new SimpleDateFormat("HH:mm");
	    Date time = null;
	    String output = "";

	    // Converting the input String to Date
	    time = dateFormat.parse(input);

	    // Changing the format of date
	    // and storing it in
	    // String
	    output = format.format(time);
	    return output;
	 }
	
	//change to 24hours format
	 public String twelveHourFormat(String time) throws ParseException 
	   {
	    	final SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
	        final Date dateObj = sdf.parse(time);
//	        log.info(new SimpleDateFormat("hh:mm a").format(dateObj));
	        return new SimpleDateFormat("hh:mm a").format(dateObj);
	    }
	
	    
	   //showTask
	public List<TaskDTO> showTask(CardDTO cardDto) throws ParseException
	{
		List<Task> taskList= taskRepository.findByCardIdAndDeleteStatus(cardDto.getCardId(), false);
		List <TaskDTO>taskDTOList=new ArrayList<>();
		 for (Task task : taskList)
		 {
	            TaskDTO taskDto1 = new TaskDTO();
	            taskDto1.setId(task.getId());
	            taskDto1.setName(task.getName());
	            taskDto1.setDescription(task.getDescription());
	            taskDto1.setStartDate(task.getStartDate());
	            taskDto1.setCardId(task.getCard().getId());
	            taskDto1.setEndDate(task.getEndDate());
	            taskDto1.setStartTime(task.getStartTime());
	            taskDto1.setEndTime(task.getEndTime());

//	            if(task.getStartTime()!= null) {
//	            	taskDto1.setStartTime(twelveHourFormat(task.getStartTime()));
//	            }
//	            if(task.getEndTime()!= null) {
//	            	taskDto1.setEndTime(twelveHourFormat(task.getEndTime()));
//	            }
//	            taskDto1.setEndDate(task.getEndDate());
//	            taskDto1.setCardId(task.getCard().getId());
	            taskDTOList.add(taskDto1);
		 }
		return taskDTOList;
	}
	
	//if date added
	public void addDateTime(TaskDTO taskDto) throws ParseException {
		Task task = new Task();
		task.setId(taskDto.getId());
		task.setStartDate(taskDto.getStartDate());
		task.setEndDate(taskDto.getEndDate());
		task.setStartTime(englishTime(taskDto.getStartTime()));
		task.setEndTime(englishTime(taskDto.getEndTime()));
		taskRepository.save(task);
	}
	
	//update date
	public void updateDate(TaskDTO taskDto,String taskId) throws ParseException
	{
		Task task=taskRepository.findByIdAndDeleteStatus(taskId,false);
		task.setId(taskId);
		task.setStartDate(taskDto.getStartDate());
		task.setEndDate(taskDto.getEndDate());
		task.setStartTime(englishTime(taskDto.getStartTime()));
		task.setEndTime(englishTime(taskDto.getEndTime()));
		taskRepository.save(task);
	}
	
	//status Condition
//	public TaskDTO setTaskAfterAddDate(TaskDTO taskDto) {
//		
//		Task task = taskRepository.findById(taskDto.getId()).get();
//		
//		taskDto.setStartDate(task.getStartDate());
//		taskDto.setStartTime(task.getStartTime());
//		taskDto.setEndDate(task.getEndDate());
//		taskDto.setEndTime(task.getEndTime());
//		
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        
//		taskDto.setStartDateTime(
//                LocalDateTime.parse(taskDto.getStartDate() + " " + taskDto.getStartTime(), dtf));
//
//		taskDto.setEndDateTime(LocalDateTime.parse(taskDto.getEndDate() + " " + taskDto.getEndTime(), dtf));
//        
//		if (taskDto.getStartDateTime().isAfter(LocalDateTime.now())) {
//            taskDto.setStatus("TO DO");
//        }
//
//        else if (LocalDateTime.now().isAfter(taskDto.getEndDateTime())) {
//        	taskDto.setStatus("Done");
//        }
//   
//        else if (LocalDateTime.now().isAfter(taskDto.getStartDateTime())
//                && LocalDateTime.now().isBefore(taskDto.getEndDateTime())) {
//        	taskDto.setStatus("Doing");
//        }
//		
//		return taskDto;
//	}
//	
	//if card name == status change the foreign
//	public void updateTaskForStatus(int boardId,TaskDTO taskDto) {
//		List<Card> cardList = cardRepository.findAllByBoardIdAndTypeAndDeleteStatus(boardId,"default", false);
//		TaskDTO taskDto1 = setTaskAfterAddDate(taskDto);
//		for(Card card: cardList) {
//		//	log.info(card.getName());
//			if(card.getName().equalsIgnoreCase(taskDto.getStatus())) {
//				Task task1 = taskRepository.findById(taskDto.getId()).get();
//				task1.setCard(card);
//				//log.info(task1.getId()+"");
//				taskRepository.save(task1);
//			}
//		}
//	}
//	
	//showOne
	public TaskDTO showOne(String taskId) 
	{
		System.out.println("task ID"+taskId);
		Task task=taskRepository.findByIdAndDeleteStatus(taskId, false);
		TaskDTO dto=new TaskDTO();
		dto.setId(task.getId());
		dto.setCardId(task.getCard().getId());
		dto.setName(task.getName());
		dto.setDescription(task.getDescription());
		dto.setStartDate(task.getStartDate());
		dto.setEndDate(task.getEndDate());
		dto.setStartTime(task.getStartTime());
		dto.setEndTime(task.getEndTime());
		return dto; 		
	}
	
	//delete 
	public void delete(String taskId)
	{
		Task task=taskRepository.findByIdAndDeleteStatus(taskId,false);
		task.setDeleteStatus(true);
		taskRepository.save(task);
	}
	
	
	
}
