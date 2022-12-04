package com.test.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.Board;
import com.test.entity.Workspace;


@Repository
@Transactional
public interface WorkspaceRepo extends JpaRepository<Workspace, Integer>{
	
	
//	@Query(value = "",nativeQuery = true)
//	List<Workspace> getAllWorkspace();
	@Query(value="select * from workspace where wid=?1 and delete_status='false'",nativeQuery = true)
	Workspace findByIdAndDeleteStatus1(int workspaceId, boolean b);	
	
	//find All
	List<Workspace> findByIdAndDeleteStatus(int workspaceId, boolean b);

	@Query(value="select * from workspace where delete_status='false'",nativeQuery = true)
	List<Workspace> findByIdAndDeleteStatus2();	
	
	
	@Query(value = "SELECT `wid` FROM `workspace` ORDER BY `wid` DESC LIMIT 1",
			nativeQuery= true)
	public Workspace selectLastRow();
	
	@Modifying
	@Query(value = "update user u join  user_work_space uws on u.uid = uws.user_id join workspace w on uws.workspace_id = w.wid \r\n"
			+ "set uws.status = 'active'  where uws.token= ?1",nativeQuery = true)
	public void acceptInvite(String token);
	
	@Query(value = "select * from user u join  user_work_space uws on u.uid = uws.user_id join workspace w on uws.workspace_id = w.wid\r\n"
	+ "where w.wid = ?1",nativeQuery = true)
	public List<Board> showMember(int id);
	
	@Query(value = "select * from user_work_space uws where uws.token = ?1",nativeQuery = true)
	public String checkToken(String token);	
	
}

