package com.test.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.NotificationE;


@Repository
public interface NotificationRepository extends JpaRepository<NotificationE,Integer> 
{

	@Query(value = "select * from notificatione where user_id = ?1 and delete_status='false'" , nativeQuery = true)
	public List<NotificationE> notiForUser(int id);

	public NotificationE findByIdAndDeleteStatus(int nfId,boolean b);
	
}
