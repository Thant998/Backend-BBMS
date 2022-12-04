package com.test.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.Task;


@Repository
public interface TaskRepository  extends JpaRepository<Task,Integer>
{
	
	List <Task> findByCardIdAndDeleteStatus(String cardId,boolean deleteStatus);
	
	Task findByIdAndDeleteStatus(String taskId, boolean b);
	
	@Query(value = "select * from task" , nativeQuery = true)
	List<Task> findByIdAndDeleteStatus1();	
	
	@Query(value="select * from task where id =?1 and card_id =?2 and delete_status='false' order by status asc;", nativeQuery = true)
	List <Task> selectAll(String taskId, String cardId,boolean deleteStatus);
	
	@Query(value = " select t.* from board b join card c on b.id = c.board_id \r\n"
		      + "join task t on t.card_id = c.id where t.delete_status = '1' \r\n"
		      + "and b.id = ?1" , nativeQuery = true)
		  List<Task> getAllDeleteTask(int id);
	
}
