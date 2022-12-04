package com.test.service;


import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.DTO.CurrentUserDTO;
import com.test.DTO.MemberDTO;
import com.test.DTO.UserDTO;
import com.test.entity.User;
import com.test.entity.UserTask;
import com.test.entity.UserWorkSpace;
import com.test.repo.UserRepo;
import com.test.repo.UserTaskRepo;
import com.test.repo.UserWorkSpaceRepository;
import com.test.repo.WorkspaceRepo;

@Service
public class UserWorkspaceService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserTaskRepo utRepo;
	
	@Autowired
	WorkspaceRepo wsRepo;
	
	@Autowired
	UserWorkSpaceRepository uwRepo;

	public void userWorkspaceAdd(int userId ) {
		//WorkspaceDTOone wsObj = new WorkspaceDTOone();
		UserWorkSpace userObj = new UserWorkSpace();
		userObj.setStatus("active");
		userObj.setUser(userRepo.findById(userId).get());
		userObj.setWorkspace(wsRepo.selectLastRow());
		uwRepo.save(userObj);		
	}
	
	public  void saveuser (UserWorkSpace uws) {
		uwRepo.save(uws);
	}
//	
//	public void UserWorkSpaceAdd(List<UserWorkSpace>  list)
//	{
//		for(UserWorkSpace l:list)
//		{
//			UserWorkSpace uws=new UserWorkSpace();
//			uws.setStatus(l.getStatus());
//			uws.setToken(l.getToken());
//			uws.setUser(l.getUser());
//			uws.setWorkspace(l.getWorkspace());
//			uwRepo.save(uws);
//		}
//	}
	public List<UserWorkSpace> showMember(int id){
		return uwRepo.showMember(id);
	}
	
	public List<MemberDTO> memberActive(int id , CurrentUserDTO cuDto){
		List<MemberDTO> memberList = new ArrayList<>();
		MemberDTO mObj1 = new MemberDTO();
//		mObj1.setCurrentUserName(cuDto.getCurrentUserName());
//		mObj1.setEmail(cuDto.getEmail());
//		mObj1.setUserId(cuDto.getUserId());
		System.out.println("Reach here ?");
		List<UserWorkSpace> userId = uwRepo.showActiveAndNoActiveMember(id);
		for (UserWorkSpace uid : userId) 
		{
			List<User> user = userRepo.memberQuery(uid.getUser().getId());
			MemberDTO mObj = new MemberDTO();
			mObj.setCurrentUserName(cuDto.getCurrentUserName());
			mObj.setEmail(cuDto.getEmail());
			mObj.setUserId(cuDto.getUserId());
			mObj.setId(uid.getId());
			mObj.setStatus(uid.getStatus());
			List<UserDTO> userList = new ArrayList<>();
			for (User u : user) 
			{
				UserDTO us = new UserDTO();
				us.setId(u.getId());
				us.setName(u.getName());
				us.setEmail(u.getEmail());
				userList.add(us);
			}
			mObj.setListUser(userList);
			memberList.add(mObj);
//			memberList.add(mObj1);
		}
		return memberList;	
	}
	
	public void deleteMember(int id) {
		UserWorkSpace userWorkspace = uwRepo.findByIdAndDeleteStatus(id , false);
		userWorkspace.setDeleteStatus(true);
		uwRepo.save(userWorkspace);
	}

	public List<MemberDTO> searchMember(int id , String name){
		List<MemberDTO> memberList = new ArrayList<>();
		//List<UserWorkSpaceDTO> userWorkspaceDTO = new ArrayList<>();
		System.out.println("Reach here ?");
		List<UserWorkSpace> userId = uwRepo.showActiveAndNoActiveMember(id);
		for (UserWorkSpace uid : userId) 
		{
			List<User> user = userRepo.searchMemberQuery(uid.getUser().getId() , name);
			MemberDTO mObj = new MemberDTO();
			mObj.setId(uid.getId());
			mObj.setStatus(uid.getStatus());
			List<UserDTO> userList = new ArrayList<>();
			for (User u : user) 
			{
				UserDTO us = new UserDTO();
				us.setName(u.getName());
				us.setEmail(u.getEmail());
				userList.add(us);
			}
			mObj.setListUser(userList);
			memberList.add(mObj);
		}
		return memberList;	
	}
	
	//Member show for active and task
//	public List<MemberDTO> showMemberActive(int id){
//		List<MemberDTO> memberList = new ArrayList<>();
//		//List<UserWorkSpaceDTO> userWorkspaceDTO = new ArrayList<>();
//		System.out.println("Reach here ?");
//		List<UserWorkSpace> userId = uwRepo.showActiveMember(id);
//		for (UserWorkSpace uid : userId) 
//		{
//			List<User> user = userRepo.memberQuery(uid.getUser().getId());
//			MemberDTO mObj = new MemberDTO();
//			mObj.setId(uid.getId());
//			mObj.setStatus(uid.getStatus());
//			List<UserDTO> userList = new ArrayList<>();
//			for (User u : user) 
//			{
//				UserDTO us = new UserDTO();
//				us.setId(u.getId());
//				us.setName(u.getName());
//				us.setEmail(u.getEmail());
//				userList.add(us);
//			}
//			mObj.setListUser(userList);
//			memberList.add(mObj);
//		}
//		return memberList;	
//	}
	
	public List<UserDTO> showMemberActive(String taskId , int workspaceId){
		List<UserDTO> memberList = new ArrayList<>();
		//List<UserWorkSpaceDTO> userWorkspaceDTO = new ArrayList<>();
		System.out.println("Reach here ?");
		List<UserTask> userTaskList  = utRepo.getTaskUserId(taskId , false);
		List<UserWorkSpace> userId = uwRepo.showActiveMember(taskId , workspaceId);
		for ( UserWorkSpace uws : userId) {
			List<User> user = userRepo.memberQuery(uws.getUser().getId());
			for (User u : user) {
				UserDTO dto = new UserDTO();
				dto.setId(u.getId());
				dto.setName(u.getName());
				memberList.add(dto);
			}
		}
		return memberList;	
	}
	

}
