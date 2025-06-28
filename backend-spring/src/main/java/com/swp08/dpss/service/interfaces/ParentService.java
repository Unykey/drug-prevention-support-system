package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;

import java.util.List;
import java.util.Optional;

public interface ParentService {
    List<Parent> findAll();
    Parent createNewParent(Parent parent);
    Optional<Parent> findByParentEmail(String email);
    Optional<Parent> findByParentPhone(String phone);
//    Optional<User> findById(Long id);
//    Optional<User> findByEmailAndPhone(String email, String phone);
//    void deleteById(Long id);

}
