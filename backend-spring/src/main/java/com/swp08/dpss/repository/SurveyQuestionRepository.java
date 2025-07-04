package com.swp08.dpss.repository;

import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
}
