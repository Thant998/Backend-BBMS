package com.test.DTO;

import java.util.List;


import lombok.Data;

@Data
public class MemberDTO {
	
	private List<UserDTO> listUser;
//	private List<UserWorkSpaceDTO> listUserWorkspace;
	private String status;
	private int id;
	private String currentUserName;
	int userId;
	String email;

}
