package com.test.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.UserTask;

@Repository
public interface UserTaskRepo extends JpaRepository<UserTask,Integer>{
	
	
	@Query(value ="select * from user_task where user_id=?1 and delete_status='false'" , nativeQuery = true)
	public List<UserTask> getUserId(int userId);
	

	@Query(value ="select * from user_task where task_id=?1 and delete_status='false'" , nativeQuery = true)
	public List<UserTask> getTaskUserId(String taskId,boolean b);
}
