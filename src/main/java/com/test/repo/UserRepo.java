package com.test.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.test.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>  
{
	
	List<User> findByEmail(String email);
	
	@Query(value = "select * from user where uid = ?1 and enable='1'" , nativeQuery = true)
	public List<User> memberQuery(int id);
	
	@Query(value = "select * from user where uid = ?1  and name like ?2 and enable='1'" , nativeQuery = true)
	public List<User> searchMemberQuery(int id , String name);
	
	
	@Query(value = "select * from user where uid = ?1 and enable='1'" , nativeQuery = true)
	public User memberQuery1(int id);
	
}


