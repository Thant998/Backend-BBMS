package com.test.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entity.Card;


@Repository
public interface CardRepository extends JpaRepository<Card,Integer>
{
	public List<Card> findAllByBoardIdAndDeleteStatus(int boardId,boolean deleteStatus);
	
	public List<Card> findAllByBoardIdAndTypeAndDeleteStatus(int boardId,String type,boolean deleteStatus);
	
	public Card findByIdAndDeleteStatus(String cardId,boolean b);
	
	@Query(value = "SELECT * FROM `card`  where type='custom'",nativeQuery= true)
	public List<Card> selectLastRow();
	
	@Query(value = "SELECT * FROM `card` where id=?1 ",nativeQuery= true)
	public Card selectSameId(String  id);
	
	@Query(value = "select * from board b join card c on b.id = c.board_id where \r\n"
			+ "b.id = ?1",nativeQuery= true)
	public List<Card> selectAll(int id);

}
