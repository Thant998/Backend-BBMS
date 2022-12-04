package com.test.repo;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.Attached;


@Repository
public interface AttachedRepository extends JpaRepository<Attached,Integer> {

	 List<Attached> findByActivityIdAndDeleteStatus(int activityId,boolean deleteStatus);

	Attached findByIdAndDeleteStatus(int attachedId , boolean f);
	
	

}
