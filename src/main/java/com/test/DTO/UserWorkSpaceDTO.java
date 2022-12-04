package com.test.DTO;

import java.util.List;
import lombok.Data;

@Data
public class UserWorkSpaceDTO {
	
	private List<WorkspaceDTO> workspace;
	private List<WorkSpaceBoardDTO> board;
	private String status;
	
	
	public List<WorkspaceDTO> getWorkspace() {
		return workspace;
	}
	public void setWorkspace(List<WorkspaceDTO> workspace) {
		this.workspace = workspace;
	}
	public List<WorkSpaceBoardDTO> getBoard() {
		return board;
	}
	public void setBoard(List<WorkSpaceBoardDTO> board) {
		this.board = board;
	}
	

	
	
	
}
