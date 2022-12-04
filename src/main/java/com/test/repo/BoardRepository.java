package com.test.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.Board;


@Repository
public interface BoardRepository extends JpaRepository<Board,Integer>
{
	
		@Query(value = "SELECT `id` FROM `board` ORDER BY `id` DESC LIMIT 1",nativeQuery= true)
		public Integer selectLastRow();
			
		public Board findByIdAndDeleteStatus(int boardId, boolean b);
		
		@Query (value ="select * from board where workspace_id =?1 and delete_status = 'false'",nativeQuery = true)
		public List<Board> allBoard(int workspaceId);
		
		@Query(value ="select * from board where name like ?1 and workspace_id =?2 and delete_status = 'false'",nativeQuery = true)
		public List<Board> search(String boardName,int workspaceId);
		
		@Query(value ="select * from board where id =?1 and delete_status = 'false'",nativeQuery = true)
		public List<Board> allBoardq(int boardId,boolean b);
		
		@Query(value ="select * from board where recent_view = ?1 and delete_status = 'false'",nativeQuery = true)
		public List<Board> recentView(String recent , boolean b);
		
		@Query(value ="select * from board where id = ?1",nativeQuery = true)
		public List<Board> selectBoard(int id);			

}
