package com.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.DTO.AttachedDTO;
import com.test.DTO.TaskAttachedDTO;
import com.test.entity.Activity;
import com.test.entity.Attached;
import com.test.repo.ActivityRepository;
import com.test.repo.AttachedRepository;


@Service
public class AttachedService {

	@Autowired
	AttachedRepository attRepo;
	
	@Autowired
	ActivityRepository activityRepo;
	
	public void saveAtt (AttachedDTO dto)
	{
		Attached attached = new Attached();
		
	
		attached.setActivity(activityRepo.findById(dto.getActivityId()).get());
		attached.setName(dto.getName());
		
		
		
		attRepo.save(attached);
	}
	
	public List<AttachedDTO> getAttachedFiles(int activityId){
		List<Attached> attachedList = attRepo.findByActivityIdAndDeleteStatus(activityId, false);
		List<AttachedDTO> attachedDTOList = new ArrayList<>();
		for(Attached attached : attachedList) {
			AttachedDTO attachedDTO = new AttachedDTO();
			attachedDTO.setId(attached.getId());
			attachedDTO.setName(attached.getName());
			attachedDTO.setActivityId(attached.getActivity().getId());
			attachedDTOList.add(attachedDTO);
		}
		return attachedDTOList;
	}
	
	public List<TaskAttachedDTO> showAttached(String taskId){
		List<TaskAttachedDTO> l = new ArrayList<>();
		List<Activity> activityList = activityRepo.selectAllTask(taskId, false);
		for ( Activity a : activityList) {
			TaskAttachedDTO dto = new TaskAttachedDTO();
			dto.setActivityId(a.getId());
			System.out.println("blablabla"+dto.getActivityId());
		List<Attached> attachedDTOList = attRepo.findByActivityIdAndDeleteStatus(a.getId(), false);
		List<AttachedDTO> attList = new ArrayList<>();
		for(Attached attached : attachedDTOList) {
			AttachedDTO attachedDTO = new AttachedDTO();
			attachedDTO.setId(attached.getId());
			attachedDTO.setName(attached.getName());
			attachedDTO.setActivityId(attached.getActivity().getId());
			attList.add(attachedDTO);
			System.out.println(attachedDTO.getId()+attachedDTO.getName());
		}
		dto.setAttach(attList);
		l.add(dto);
		}	
		return l;
	}

	public void deleteAttached(int attachedId) {
		Attached a = attRepo.findByIdAndDeleteStatus(attachedId , false);
		a.setId(attachedId);
		a.setDeleteStatus(true);
		attRepo.save(a);		
	}
		
	public void saveat(Attached at) {
		 attRepo.save(at);
	}
	
	public Attached findatt(int id) {
		return attRepo.findById(id).get();
	}
	
	
}
