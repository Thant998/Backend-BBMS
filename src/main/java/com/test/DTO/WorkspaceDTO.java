package com.test.DTO;


import java.util.List;

import lombok.Data;

@Data
public class WorkspaceDTO 
{
	private int workspaceId;
	private String workspaceName;
	private List<BoardDTO> boards;
	private int userId;
	private String description;
	private String[] inviteEmail;
		
}
