package com.test.controller;

import java.io.IOException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.DTO.AttachedDTO;
import com.test.DTO.TaskAttachedDTO;
import com.test.entity.Attached;
import com.test.repo.UserRepo;
import com.test.service.ActivityService;
import com.test.service.AttachedService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class AttachedController {
	
	@Autowired
	AttachedService attachedService;
	
	@Autowired
	ActivityService as;
	
	@Autowired
	UserRepo repo;
	
//	@PostMapping("/attached/addpost")
//	public void uploadfile(@RequestBody AttachedDTO dto) throws IOException {
//		;
//		
//		Attached att = new Attached();
//		att.setActivity(as.getat(dto.getActivityId()));
//		att.setName(dto.getFiles().getOriginalFilename());
//		att.setResource(dto.getFiles().getBytes());
//		att.setDeleteStatus(false);
//		attachedService.saveat(att);
	
	
	
	@PostMapping("/attached/addpost")
	public void uploadfile(@RequestParam("files")MultipartFile  file,@RequestParam("activityId")int id) throws IOException {
		;
		Attached att = new Attached();
		att.setActivity(as.getat(id));
		att.setName(file.getOriginalFilename());
		att.setResource(file.getBytes());
		att.setDeleteStatus(false);
		attachedService.saveat(att);				
	}
	
//	   @PostMapping("/attached/addpost")
//	    public void uploadMultipartFile(@RequestParam("files")MultipartFile  file,@RequestParam("activityId")int id) {
//	        try {
//	            AttachedDTO attachedDTO = new AttachedDTO();
//	            attachedDTO.setActivityId(id);
//	            System.out.println();
//	            
//	          //  for (MultipartFile file : multipartFiles) {
//	                if (!file.isEmpty()) {
//	             //  Attached attached = new Attached();
//	               attachedDTO.setName(file.getOriginalFilename());
//	               attachedService.saveAtt(attachedDTO);   
//	                }
//	       //     }
//
//	            String uploadDir = "src/main/resources/static/" + attachedDTO.getActivityId();
//	            Path uploadPath = Paths.get(uploadDir);
//
//	            if (!Files.exists(uploadPath)) {
//	                Files.createDirectories(uploadPath);
//	            }
//
//	//            for (MultipartFile file : multipartFiles) {
//	            	
//	                if (!file.isEmpty()) {
//	                    try (InputStream inputStream = file.getInputStream()) {
//	                        Path filePath = uploadPath.resolve(file.getOriginalFilename());
//	                        System.out.println(filePath.toFile().getAbsolutePath());
//	                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//	                    } catch (IOException e) {
//	                        throw new IOException("Could not save upload file: " + file.getOriginalFilename());
//	                    }
//	                }
//	                
//	                
//	  //          }
//	            
//	            
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }   
//	    }
	   
	   	
	   @GetMapping("/download/{id}")
	   public Attached down (@PathVariable("id") String id){
		  return  attachedService.findatt(Integer.valueOf(id));
	   }
	  

	   
	   
	   @GetMapping(value="/attached/getAll/{activityId}")
		public List<AttachedDTO> getAllFileByActivityId (@PathVariable int activityId)
		{
		   
			return attachedService.getAttachedFiles(activityId);
		}
	   
	   @GetMapping(value="/attached/showAll/{taskId}")
		public List<TaskAttachedDTO> showAttached (@PathVariable String taskId)
		{	   
		   System.out.println("ttttttttttttttttt"+taskId);
			return attachedService.showAttached(taskId);
		}
	   
	   
	   @GetMapping(value="/deleteAttached/{attachedId}")
		public void deleteAttached (@PathVariable int attachedId)
		{
		   System.out.println("dddddddddddddd"+attachedId);
			attachedService.deleteAttached(attachedId);
			 System.out.println("dddddddddddddd"+attachedId);
		}

}
