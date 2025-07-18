package com.swp08.dpss.repository.survey;

import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.SurveyTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByStatus(SurveyStatus status);

    List<Survey> findByNameContainingIgnoreCase(String name);

    Survey findByName(String name);

    List<Survey> findAllByStatusAndType(SurveyStatus status, SurveyTypes type);

    List<Survey> findByType(SurveyTypes type);
}
