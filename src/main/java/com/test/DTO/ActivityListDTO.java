package com.test.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ActivityListDTO {

	List<ActivityDTO> list;
	private String taskId;
	private String cardId;
	
}
