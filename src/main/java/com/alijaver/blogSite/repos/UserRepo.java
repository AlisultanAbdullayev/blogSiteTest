package com.alijaver.blogSite.repos;

import com.alijaver.blogSite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername (String username);
}
