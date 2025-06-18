package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.ParentCreationRequest;
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
    public Parent createNewParent(ParentCreationRequest parent) {
        Parent newParent = new Parent();
        newParent.setParentName(parent.getParentName());
        newParent.setParentEmail(parent.getParentEmail());
        newParent.setParentPhone(parent.getParentPhone());
        return parentRepository.save(newParent);
    }

    @Override
    public Optional<Parent> findByParentEmail(String email) {
        return parentRepository.findByParentEmail(email);
    }

    @Override
    public Optional<Parent> findByParentId(Long id) {
        return parentRepository.findByParentId(id);
    }
}
