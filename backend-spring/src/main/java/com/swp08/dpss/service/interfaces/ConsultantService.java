package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.entity.consultant.Consultant;

import java.util.List;

public interface ConsultantService {
    List<Consultant> findAll();

    Consultant createNewConsultant(Consultant consultant);
}
