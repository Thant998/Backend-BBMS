package com.test.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.DTO.ActivityDTO;
import com.test.DTO.ActivityListDTO;
import com.test.entity.Activity;
import com.test.entity.Attached;
import com.test.entity.Task;
import com.test.repo.ActivityRepository;
import com.test.repo.TaskRepository;


@Service
public class ActivityService 
{
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	
	//insert and update
	public void insertActivity (ActivityListDTO listDto)
	{		
		for(ActivityDTO ALD : listDto.getList())
		{
			Activity act=activityRepository.findByIdAndDeleteStatus(ALD.getActivityId(), false);
			if(act==null)
			{
				Activity activity=new Activity();
				Task task=taskRepository.findByIdAndDeleteStatus(ALD.getTaskId(),false);
				activity.setId(ALD.getActivityId());  //new add id
				activity.setName(ALD.getActivityName());
				activity.setTask(task);
				activity.setDeleteStatus(false);
				activity.setStatus(ALD.getStatus());
				activityRepository.save(activity);
			}
			else
			{
				Activity activity=activityRepository.findByIdAndDeleteStatus(ALD.getActivityId(), false);
				Task task=taskRepository.findByIdAndDeleteStatus(ALD.getTaskId(),false);
				activity.setId(ALD.getActivityId());
				activity.setName(ALD.getActivityName());
				activity.setTask(task);
				activity.setDeleteStatus(false);
				activity.setStatus(ALD.getStatus());
				activityRepository.save(activity);				
			}
		}
		
	}	
	
	
	

	//showAll
	public List<ActivityDTO> showAll(ActivityDTO dto)
	{
		List<Activity> act=activityRepository.selectAllTask(dto.getTaskId(), false);
		List<ActivityDTO> list=new ArrayList<>();
		for(Activity a: act)
		{	
			ActivityDTO dto1=new ActivityDTO();
			dto1.setActivityName(a.getName());
			dto1.setActivityId(a.getId());
			dto1.setStatus(a.getStatus());
			dto1.setTaskId(a.getTask().getId());
			list.add(dto1);
		}
		return list;
	}
	
	//showOne
	public ActivityDTO showOne(int activityId)
	{
		Activity act =activityRepository.findByIdAndDeleteStatus(activityId, false);
		ActivityDTO dto=new ActivityDTO();
		dto.setActivityId(act.getId());
		dto.setActivityName(act.getName());
		dto.setStatus(act.getStatus());
		return dto;
		
	}
		
	//delete
	public void Delete(int activityId)
	{
		Activity act =activityRepository.findByIdAndDeleteStatus(activityId, false);
		act.setDeleteStatus(true);
		activityRepository.save(act);
	}
	
	public Activity getat(int id ) {
		return activityRepository.findByIdAndDeleteStatus(id ,false);
	}
	
	
	
}
