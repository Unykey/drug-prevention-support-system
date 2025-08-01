package com.swp08.dpss.service.interfaces.consultant;

import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.entity.consultant.Qualification;
import com.swp08.dpss.entity.consultant.Specialization;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ConsultantService {
    List<Consultant> findAll();

    void createNewConsultant(Long userId, Consultant consultant);

    Consultant createNewConsultant2(Long userId,
                              Consultant consultant,
                              Set<Availability> availabilities,
                              Set<Specialization> specializations,
                              Qualification qualification);

    String saveProfilePicture(Long consultantId, MultipartFile profilePicture);
}
