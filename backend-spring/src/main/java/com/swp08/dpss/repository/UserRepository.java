package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteUserById(Long Id);

    boolean existsUserByEmail(String email);
}
