package com.swp08.dpss.repository;

import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.SurveyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByNameContainingIgnoreCase(String name);

    List<Survey> findByStatus(SurveyStatus status);
}
