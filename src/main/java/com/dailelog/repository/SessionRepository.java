package com.dailelog.repository;

import com.dailelog.domain.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Optional<Session> findByAccessToken(String accessToken);
}
