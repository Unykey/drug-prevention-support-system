package com.swp08.dpss.repository.consultant;

import com.swp08.dpss.entity.consultant.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
}
