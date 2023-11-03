package com.javatechie.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.javatechie.entity.UserInfo;

public interface UserInfoRepository extends MongoRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}
