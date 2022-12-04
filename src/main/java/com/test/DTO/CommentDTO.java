package com.test.DTO;

import lombok.Data;

@Data
public class CommentDTO {

	private int id;
	private String name;
	private String userName;
	private String date;
	private String time;
	private String taskId;
	private int userId;
}
