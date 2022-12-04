package com.test.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UserTaskList {

//	private String userId;
//	private String userName;
	private List<UserDTO> userList;
	private String id;
	private String name;
	private String startDate;
	private String endDate;
	private String cardId;
	private String startTime;
	private String endTime;
	private String description;
	

}
