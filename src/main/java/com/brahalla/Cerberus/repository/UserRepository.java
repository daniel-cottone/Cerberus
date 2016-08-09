package com.brahalla.Cerberus.repository;

import com.brahalla.Cerberus.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
