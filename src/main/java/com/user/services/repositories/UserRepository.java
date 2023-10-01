package com.user.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.services.Entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
