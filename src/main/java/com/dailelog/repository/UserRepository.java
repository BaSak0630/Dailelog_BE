package com.dailelog.repository;

import com.dailelog.domain.User;
import com.dailelog.service.AuthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByAccountAndPassword(String account,String password);
    Optional<User> findByAccount(String account);
}
