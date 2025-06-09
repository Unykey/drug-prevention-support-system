package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    Optional<Parent> findByParentEmail(String email);
    Optional<Parent> findByParentPhone(String phone);
//    void deleteParentById(Long Id);
//    Optional<User> deleteUserByEmail(String email);
//    Optional<User> deleteUserByPhone(String phone);
}
