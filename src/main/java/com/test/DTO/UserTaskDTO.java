package com.test.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UserTaskDTO {

	//private int id;
	private String taskId;
	private List<Integer> userId;
	
	
}
