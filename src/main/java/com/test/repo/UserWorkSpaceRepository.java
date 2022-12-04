package com.test.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.test.entity.UserWorkSpace;


@Repository
public interface UserWorkSpaceRepository extends JpaRepository<UserWorkSpace,Integer>
{
	@Query(value = "SELECT * FROM  user_work_space where user_id =?1 and status = 'active'",nativeQuery= true)
	public List<UserWorkSpace> workId(int userId);	

	@Query(value = "select * from user u join  user_work_space uws on u.uid = uws.user_id join workspace w on uws.workspace_id = w.wid\r\n"
			+ "where w.wid = ?1",nativeQuery = true)
	public List<UserWorkSpace> showMember(int id);
	
	
	@Query(value = "SELECT * FROM  user_work_space where workspace_id =?1 and delete_status='false' group by user_id ",
			nativeQuery= true)
	public List<UserWorkSpace> showActiveAndNoActiveMember(int workspaceId);


	public UserWorkSpace findByIdAndDeleteStatus(int id, boolean b);
	
//	
//	@Query(value = "SELECT * FROM  user_work_space where workspace_id =?1 and delete_status='false' and status='active' group by user_id ",
//			nativeQuery= true)
//	public List<UserWorkSpace> showActiveMember(int workspaceId);
	

	@Query(value = "select  * from user_work_space where user_id not in (select user_id from user_task where task_id=?1 ) \n"
			+ "and user_work_space.workspace_id  = ?2 and user_work_space.delete_status = 'false';",
			nativeQuery= true)
	public List<UserWorkSpace> showActiveMember(String taskId , int workspaceId);

	
}
