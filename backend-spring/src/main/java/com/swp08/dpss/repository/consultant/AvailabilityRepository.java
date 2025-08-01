package com.swp08.dpss.repository.consultant;

import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.consultant.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
