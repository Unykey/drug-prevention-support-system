package com.swp08.dpss.repository;

import com.swp08.dpss.entity.client.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteUserById(Long Id);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhone(String phoneNumber);

    List<User> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase (String name, String email);
    List<User> findByNameContainingIgnoreCase (String name);
    List<User> findByEmailContainingIgnoreCase (String email);

}
