package com.li.delivery.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.li.delivery.user.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {

    UserModel findUserByUserName(String username);
}
