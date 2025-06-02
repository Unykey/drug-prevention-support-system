package com.swp08.dpss.repository;

import com.swp08.dpss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
