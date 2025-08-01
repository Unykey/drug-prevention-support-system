package com.swp08.dpss.repository.program;

import com.swp08.dpss.entity.program.ProgramSurvey;
import com.swp08.dpss.entity.program.ProgramSurveyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramSurveyRepository extends JpaRepository<ProgramSurvey, ProgramSurveyId> {
}
