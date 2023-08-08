package com.cineme.cinemeapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineme.cinemeapp.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	@Query(value="select u from Users u where u.email=?1 and u.password=?2")
	Users userLogin(String emailId, String password);
	
	@Query(value="select u from Users u where u.email=?1")
	Users getUserByEmail(String emailId);
}
