package com.test.DTO;

import java.util.List;

import lombok.Data;

@Data
public class TaskAttachedDTO {
	
	List<AttachedDTO> attach ;
	int activityId;
}
