package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.enums.TargetGroupName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetGroupRepository extends JpaRepository<TargetGroup, Long> {

    Optional<TargetGroup> findByTargetGroupName(TargetGroupName enumName);
}
