package com.test.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.test.DTO.CurrentUserDTO;
import com.test.DTO.MemberDTO;
import com.test.DTO.UserDTO;
import com.test.DTO.WorkspaceDTO;
import com.test.email.EmailConfig;
import com.test.entity.User;
import com.test.entity.UserWorkSpace;
import com.test.entity.Workspace;
import com.test.repo.UserRepo;
import com.test.service.BoardService;
import com.test.service.UserService;
import com.test.service.UserWorkspaceService;
import com.test.service.WorkspaceService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class WorkspaceController 
{
	
	@Autowired
	EmailConfig mail;
	
	@Autowired
	UserService us;
	
	@Autowired
	WorkspaceService workspaceService;	
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserRepo Ur;
	
	@Autowired
	UserWorkspaceService uwService;

	
	@GetMapping(value="showAllWorkSpace", produces = "application/json")
	public ResponseEntity<List<WorkspaceDTO>> showAll() throws NumberFormatException, ParseException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		return ResponseEntity.ok(workspaceService.workspaceId(user.getId()));
	}
	
	
	@PostMapping("/createWorkspace")
	@ResponseStatus(value = HttpStatus.OK )
	public String createWorkspace(@RequestBody Workspace workspace) 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		workspaceService.createWorkspace(workspace);
		uwService.userWorkspaceAdd(user.getId());
		return "success";
	}
	
	
	@RequestMapping("/acceptInvite/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView acceptInvitation(@PathVariable("id")String token) 
	{
		ModelAndView modelAndView = new ModelAndView();
		String tok = workspaceService.checkToken(token);
		System.out.println("smth" + tok);
		if( tok != null  ) 
		{
			workspaceService.acceptInvite(token);
			
		      modelAndView.setViewName("success");
		      return modelAndView;
		}
		 modelAndView.setViewName("fail");
		return modelAndView;
	}
	
	
	
	
	@PostMapping("/showWorkspacemember")
	@ResponseStatus(value = HttpStatus.OK)
	public List<UserWorkSpace> ShowMember(@RequestBody Workspace workspace) 
	{
		return uwService.showMember(workspace.getId());
	}
	
	@PostMapping("/addMember")
	@ResponseStatus(value = HttpStatus.OK)
	public String memebers(@RequestBody Workspace ws) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		Workspace workspace  =  workspaceService.getWorkspace(ws.getId());
		System.out.println(ws.getInviteEmail());
		List<UserWorkSpace> list = new ArrayList<>();
		for(String email : ws.getInviteEmail()) {
			UUID uuid1 = UUID.randomUUID();
			list.add(UserWorkSpace.builder()
			.workspace(workspace).user(us.findbyEmail(email).get(0))
			.status("inactive").token(uuid1.toString()).build());
			mail.simpleMail(email,"http://localhost:8080/api/v1/acceptInvite/"+uuid1.toString() ,"for project to accept invitation");
		}
		uwService.saveuser(list.get(0));
		return user.getName();
	}
	
	
//	@PostMapping("/addMember")
//	@ResponseStatus(value = HttpStatus.OK)
//	public String AddMember(@RequestBody Workspace ws) 
//	{
//		System.out.println("Id" + " "+ ws.getId());
//		System.out.println("Invite email" + " "+ ws.getInviteEmail());
//		System.out.println("Name" + " "+ ws.getName());
//		
//		Workspace workspace  =  workspaceService.getWorkspace(ws.getId());
//		List<UserWorkSpace> list = new ArrayList<>();
//		
//		if(ws.getInviteEmail().length > 0) 
//		{
//			for(String email : ws.getInviteEmail()) 
//			{
//				UUID uuid1 = UUID.randomUUID();
//			//mail.simpleMail(email,"http://localhost:8080/api/v1/acceptInvite/"+uuid1.toString() ,"for project to accept invitation");
//	                   list.add(UserWorkSpace.builder()
//						.workspace(workspace).user(us.findbyEmail(email).get(0))
//						.status("inactive").token(uuid1.toString()).build()); 
//			}
//		}
//		workspace.setWorkspaceUser(list);
//		System.out.println(list);
//		uwService.saveuser(list.get(0));
//		//workspaceService.createWorkspace(workspace);
//		return "success";
//	}
	
	
	@GetMapping("/memberWorkspace/{workspaceId}")
	public WorkspaceDTO memberWorkspace(@PathVariable int workspaceId)
	{
		System.out.println(workspaceId);
		return workspaceService.member(workspaceId);
	}
	
	
	@GetMapping("/showMemberList/{workspaceId}")
	public List<MemberDTO> showMemberLists(@PathVariable int workspaceId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		User user = Ur.findByEmail(auth.getName()).get(0);
		CurrentUserDTO dto = new CurrentUserDTO();
		dto.setCurrentUserName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setUserId(user.getId());
		return uwService.memberActive(workspaceId , dto);
	}
	
	
	@GetMapping("/deleteMember/{id}")
	public void deleteMember(@PathVariable int id) 
	{
		uwService.deleteMember(id);
	}
	
	
	@GetMapping("/searchMember/{id}")
	public List<MemberDTO> memberSearch(@RequestParam(value="name",required=false)String name,@PathVariable int id)
	{
		return uwService.searchMember(id, "%"+name+"%");
	}
	
	//အရင်က 
//	@GetMapping("/showActiveMemberList/{workspaceId}")
//	public List<MemberDTO> showActiveMemberLists(@PathVariable int workspaceId){
//		System.out.println("workspace id  " + workspaceId);
//		return uwService.showMemberActive(workspaceId);
//	}
	
	@PutMapping("/UpdateWorkspace")
	public void workspaceUpdate(@RequestBody WorkspaceDTO dto)
	{
		System.out.println("id id id id workspace"+ dto.getWorkspaceId()+dto.getWorkspaceName());
//		dto.setWorkspaceId(3);
//		dto.setWorkspaceName("A");
		workspaceService.workspaceUpdate(dto);
		
	}
	
	@GetMapping("/deleteWorkspace/{workspaceId}")
	public void workspaceDelete(@PathVariable int workspaceId)
	{
		workspaceService.workspaceDelete(workspaceId);
	}
	
	//task member 
		@GetMapping("/showActiveMemberList/{workspaceId}/{taskId}")
		public List<UserDTO> showActiveMemberLists(@PathVariable int workspaceId , @PathVariable String taskId){
			System.out.println("workspace id  " + workspaceId);
			return uwService.showMemberActive(taskId , workspaceId);
		}
		
}
