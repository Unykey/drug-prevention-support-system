package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByNameContainingIgnoreCase(String name);

    void deleteById(long id);
}
