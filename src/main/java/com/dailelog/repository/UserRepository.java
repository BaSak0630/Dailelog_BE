package com.dailelog.repository;

import com.dailelog.domain.User;
import com.dailelog.service.AuthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLoginIdAndPassword(String loginId,String password);
    Optional<User> findByLoginId(String loginId);
}
