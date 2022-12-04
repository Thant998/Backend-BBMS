package com.test.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.NotificationDTO;
import com.test.entity.NotificationE;
import com.test.repo.NotificationRepository;
import com.test.repo.UserRepo;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository nRp;
	
	@Autowired
	UserService uSservice;
	
	@Autowired
	UserRepo uSr;
	public void NotificationInsert(NotificationDTO dto)
	{
		NotificationE nf = new NotificationE();
		nf.setName(dto.getName());
		nf.setType(dto.getType());
		nf.setUser(uSr.memberQuery1(dto.getUserId()));
		nRp.save(nf);
	}
	
	public List<NotificationDTO> selectAll(int userId)
	{
		List<NotificationE> n=nRp.notiForUser(userId);
		List<NotificationDTO> list=new ArrayList<>();
		for(NotificationE nf:n)
		{
			NotificationDTO  dto=new NotificationDTO();
			dto.setId(nf.getId());
			dto.setName(nf.getName());
			dto.setType(nf.getType());
			dto.setUserId(userId);
			list.add(dto);
		}
		return list;
		
	}
	public void delete(int nfId)
	{
		NotificationE nfE=nRp.findByIdAndDeleteStatus(nfId,false);
		nfE.setDeleteStatus(true);
		nRp.save(nfE);
	}

}

