package com.test.DTO;

import com.test.entity.User;
import com.test.entity.Workspace;

import lombok.Data;

@Data
public class MemberInviteDTO {
	
	private int id;
	private User user;
	private Workspace workspace;
	private boolean deleteStatus ;
	private String status; 
	private String token;
	
}
