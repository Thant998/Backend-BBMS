package com.test.DTO;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AttachedDTO {
	private int id;
	private String name;
	//private MultipartFile[] files;
	private int activityId;
	private MultipartFile files;
}
