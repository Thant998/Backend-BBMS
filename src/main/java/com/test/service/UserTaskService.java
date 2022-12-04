package com.test.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.CardDTO;
import com.test.DTO.TaskDTO;
import com.test.DTO.UserDTO;
import com.test.DTO.UserTaskDTO;
import com.test.DTO.UserTaskList;
import com.test.entity.Task;
import com.test.entity.User;
import com.test.entity.UserTask;
import com.test.repo.TaskRepository;
import com.test.repo.UserRepo;
import com.test.repo.UserTaskRepo;

@Service
public class UserTaskService {

	@Autowired
	private UserTaskRepo userTaskRepo;

	@Autowired
	private TaskRepository aRepo;

	@Autowired
	private UserRepo userRepo;
	
	//for invite user
	public void insertUser(UserTaskDTO dto) {
		UserTask ua = new UserTask();
		ua.setTask(aRepo.findByIdAndDeleteStatus(dto.getTaskId(), false));
		List<Integer> uid = dto.getUserId();
		for (int i : uid) {
			ua.setUser(userRepo.findById(i).get());
		}
		ua.setDeleteStatus(false);
		userTaskRepo.save(ua);
	}

	//for private user
	public void privateUser(int userId,String taskId)
	{
		UserTask ua = new UserTask();
		User u=userRepo.findById(userId).get();
		ua.setUser(u);
		
		Task t=aRepo.findByIdAndDeleteStatus(taskId, false);
		ua.setTask(t);
		
		userTaskRepo.save(ua);
	}
	//for TaskShow for current user
	public List<TaskDTO> showUsers(CardDTO cardId, int userId) {
		List<TaskDTO> taskDtoList = new ArrayList<>();
		List<UserTask> list = userTaskRepo.getUserId(userId);
		for (UserTask userTask : list) {
			List<Task> task = aRepo.selectAll(userTask.getTask().getId(), cardId.getCardId(), false);
			for (Task utask : task) {
				TaskDTO taskDto = new TaskDTO();
				taskDto.setCardId(utask.getCard().getId());
				taskDto.setDescription(utask.getDescription());
				taskDto.setEndDate(utask.getEndDate());
				taskDto.setStartDate(utask.getStartDate());
				taskDto.setStartTime(utask.getEndTime());
				taskDto.setId(utask.getId());
				taskDto.setName(utask.getName());
				taskDtoList.add(taskDto);
			}
		}
		return taskDtoList;
	}
	
	
	//for Member Show
	public UserTaskList showTaskMember(String taskId){
		UserTaskList utl = new UserTaskList();
		
		//List<TaskDTO> t = new ArrayList<>();
		List<UserTask> userTasks = userTaskRepo.getTaskUserId(taskId,false);
		Task task = aRepo.findByIdAndDeleteStatus(taskId , false);
		
		//UserTaskList obj = new UserTaskList();
		//obj.setCardId(task.getCard().getId());
		utl.setName(task.getName());
		utl.setStartDate(task.getStartDate());
		utl.setEndDate(task.getEndDate());
		utl.setStartTime(task.getStartTime());
		utl.setEndTime(task.getEndTime());
		utl.setDescription(task.getDescription());
	System.out.println("this is name for task"+utl.getName());
		List<UserDTO> listUserDto = new ArrayList<>();
		
		for(UserTask ut : userTasks) {
			List<User> lu = userRepo.memberQuery(ut.getUser().getId());
			for (User u : lu) {
				UserDTO uObj = new UserDTO();
				uObj.setId(u.getId());
				uObj.setName(u.getName());
				System.out.println("this is name for task"+uObj.getName());
				listUserDto.add(uObj);
			}	
		}
		utl.setUserList(listUserDto);
		return utl;
	}
		

}
