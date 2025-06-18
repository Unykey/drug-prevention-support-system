package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.ParentCreationRequest;
import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;

import java.util.List;
import java.util.Optional;

public interface ParentService {
    List<Parent> findAll();

    Parent createNewParent(ParentCreationRequest parent);

    Optional<Parent> findByParentEmail(String email);

    Optional<Parent> findByParentId(Long id);
}
