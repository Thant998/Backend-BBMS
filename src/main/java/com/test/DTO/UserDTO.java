package com.test.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	private String name;
	private String email;
	private String password;
	private String confirm;
	private String role;
	private boolean  enable;
	private byte[] pic;
	private String picname;
	
	
	
}
