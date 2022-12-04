package com.test.service;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.BoardDTO;
import com.test.DTO.WorkspaceDTO;
import com.test.entity.Board;
import com.test.entity.UserWorkSpace;
import com.test.entity.Workspace;
import com.test.repo.BoardRepository;
import com.test.repo.UserRepo;
import com.test.repo.UserWorkSpaceRepository;
import com.test.repo.WorkspaceRepo;


@Service
public class WorkspaceService {
	
	@Autowired
	WorkspaceRepo workspaceRepository;
		
	@Autowired
	UserRepo userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserWorkSpaceRepository uwsR;
	
	
	public void createWorkspace(Workspace workspace) {
		System.out.println(workspace.getId());
		workspaceRepository.save(workspace);	
	}
	
	public Workspace getWorkspace(int id) {
		return workspaceRepository.findById(id).get();
	}
	
	
	public String checkToken(String token) {
		return workspaceRepository.checkToken(token);
	}
	
	public void acceptInvite(String token) {
		workspaceRepository.acceptInvite(token);
	}
	
	public WorkspaceDTO member(int id) {
		Workspace w = workspaceRepository.findByIdAndDeleteStatus1(id , false);
		WorkspaceDTO dto = new WorkspaceDTO();
		dto.setWorkspaceId(w.getId());
		dto.setWorkspaceName(w.getName());
		return dto;
		
	}
	
	public void workspaceUpdate(WorkspaceDTO dto)
	{
		System.out.println("it is workspace id"+dto.getWorkspaceId());
		Workspace w = workspaceRepository.findByIdAndDeleteStatus1(dto.getWorkspaceId(), false);
		w.setId(dto.getWorkspaceId());
		w.setName(dto.getWorkspaceName());
		workspaceRepository.save(w);
	}
	
	public void workspaceDelete(int id)
	{
		Workspace w = workspaceRepository.findByIdAndDeleteStatus1(id, false);
		w.setDeleteStatus(true);
		workspaceRepository.save(w);
	}
	
	
	
//
//	public List<Workspace> getAllWorkSpace(){
//		List <Workspace> list=new ArrayList<>();
//		List <Workspace> l=workspaceRepository.findByIdAndDeleteStatus2();
//		for(Workspace i:l)
//		{
//			Workspace wk=new Workspace();
//			wk.setId(i.getId());
//			wk.setName(i.getName());
//			//List <String> bs=new ArrayList<>();
//			for( Board b:i.getBoard())
//			{
//				wk.setbName(b.getName());
//			}
//			list.add(wk);
//			
//		}
//	  return list;
//	}
	
//	
//	public List<UserWorkSpaceDTO> workspaceGet(int userId)
//	{
//		List<UserWorkSpaceDTO> listUser=new ArrayList<>();
//		List<UserWorkSpace> uws=uwsR.workId(userId);
//	
//		for(UserWorkSpace wId:uws)
//		{
//			System.out.println(wId.getId());
//			UserWorkSpaceDTO udto=new UserWorkSpaceDTO();
//			List<Workspace> workspaceList=workspaceRepository.findByIdAndDeleteStatus(wId.getWorkspace().getId(),false);
//			List<WorkspaceDTO> list=new ArrayList<>();
//			for(Workspace wL:workspaceList)
//			{
//				WorkspaceDTO dto=new WorkspaceDTO();
//				dto.setWorkspaceId(wL.getId());
//				dto.setWorkspaceName(wL.getName());
//				list.add(dto);
//				System.out.println(dto);
//				List<WorkSpaceBoardDTO> bList=new ArrayList<>();
//				
//				for(Board b:wL.getBoard())
//				{
//					WorkSpaceBoardDTO dtoB=new WorkSpaceBoardDTO();
//					dtoB.setBoardId(b.getId());
//					dtoB.setBoardName(b.getName());
//					bList.add(dtoB);
//				}
//				udto.setBoard(bList);
//				udto.setWorkspace(list);
//			}
//			listUser.add(udto);
//		}
//		return listUser;
//		
//	}
	public List<WorkspaceDTO> workspaceId(int userId)
	{
		List<WorkspaceDTO> WorkspaceId=new ArrayList<>();
		//workspaceId from userId<UserWorkSpace>
		List<UserWorkSpace> userW=uwsR.workId(userId);
		
		
		for(UserWorkSpace w:userW)
		{
			System.out.println("Hiii"+w.getId());
			List<Workspace> workspaceList=workspaceRepository.findByIdAndDeleteStatus(w.getWorkspace().getId(),false);
			for(Workspace wId:workspaceList)
			{
				WorkspaceDTO dto=new WorkspaceDTO();
				dto.setWorkspaceId(wId.getId());
				dto.setWorkspaceName(wId.getName());
				List<BoardDTO> listB=new ArrayList<>();
				for(Board b:wId.getBoard())
				{
					List<Board> board = boardRepository.allBoardq(b.getId(), false);
					for (Board b1:board) {
					BoardDTO bDto=new BoardDTO();
					bDto.setBoardId(b1.getId());
					bDto.setBoardName(b1.getName());
					listB.add(bDto);
				}
				}
				dto.setBoards(listB);
				WorkspaceId.add(dto);
			}
			
		}
		return WorkspaceId;
	}
	
	
}
