package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.enums.SurveyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByNameContainingIgnoreCase(String name);

    List<Survey> findByStatus(SurveyStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Survey s SET s.status = :status WHERE s.id = :id")
    void softDeleteSurveyById(@Param("id") Long id, @Param("status") SurveyStatus status);

}
