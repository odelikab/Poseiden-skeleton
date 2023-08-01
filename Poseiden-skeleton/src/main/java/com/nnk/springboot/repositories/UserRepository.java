package com.nnk.springboot.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nnk.springboot.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	Optional<User> findByUsername(String username);

	@Modifying
	@Transactional
	@Query(value = "insert into authorities(id,username,authority) VALUES (?, ?, ?)", nativeQuery = true)
	void saveAuthority(int id, String username, String authority);

	@Modifying
	@Transactional
	@Query(value = "update authorities set authority=? where id=?", nativeQuery = true)
	void updateAuthority(String authority, int id);

}
