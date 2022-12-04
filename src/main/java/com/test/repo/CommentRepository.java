package com.test.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.test.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> 
{

	Comment findByIdAndDeleteStatus(int commentId, boolean b);
	
	@Query ( value="select * from comment where task_Id=?1 and delete_status='false'" , nativeQuery = true)
	List<Comment> selectAllComment(String taskId);

}
