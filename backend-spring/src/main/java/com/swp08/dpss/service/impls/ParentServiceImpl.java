package com.swp08.dpss.service.impls;

import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.repository.ParentRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.ParentService;
import com.swp08.dpss.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {
    @Autowired
    ParentRepository parentRepository;

    @Override
    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    @Override
    public Parent createNewParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public Optional<Parent> findByParentEmail(String email) {
        return parentRepository.findByParentEmail(email);
    }

    @Override
    public Optional<Parent> findByParentPhone(String phone) {
        return parentRepository.findByParentPhone(phone);
    }
}
