package com.test.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.Activity;


@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer>
{
	
	Activity findByIdAndDeleteStatus(int activityId,boolean b);

	List<Activity> findByTaskIdAndDeleteStatus(int taskId, boolean b);
	
	@Query(value="select * from activity where task_id=?1 and delete_status='false'",nativeQuery=true)
	List<Activity> selectAllTask(String taskId,boolean b);
	
	

	
	
}
