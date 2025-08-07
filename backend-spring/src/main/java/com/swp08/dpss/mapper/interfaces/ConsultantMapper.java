package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.consultant.AvailabilityResponse;
import com.swp08.dpss.dto.responses.consultant.ConsultantResponse;
import com.swp08.dpss.dto.responses.consultant.QualificationResponse;
import com.swp08.dpss.dto.responses.consultant.SpecializationResponse;
import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.entity.consultant.Qualification;
import com.swp08.dpss.entity.consultant.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ConsultantMapper {
    @Mapping(target = "consultantId", expression = "java(cons.getConsultantId())")
    @Mapping(target = "bio", expression = "java(cons.getBio())")
    @Mapping(target = "profilePicture", expression = "java(cons.getProfilePicture())")
    ConsultantResponse toResponse(Consultant cons);

    List<ConsultantResponse> toResponseList(List<Consultant> consList);

    Set<AvailabilityResponse> toAvailabilityResponseSet(Set<Availability> avaiList);
    Set<QualificationResponse> toQualificationResponseSet(Set<Qualification> qualList);
    Set<SpecializationResponse> toSpecializationResponseSet(Set<Specialization> specList);

    @Mapping(target = "availabilityId", expression = "java(avai.getAvailabilityId())")
    @Mapping(target = "date", expression = "java(avai.getDate())")
    @Mapping(target = "startTime", expression = "java(avai.getStartTime())")
    @Mapping(target = "endTime", expression = "java(avai.getEndTime())")
    @Mapping(target = "isAvailable", expression = "java(avai.isAvailable())")
    AvailabilityResponse toResponse(Availability avai);

    @Mapping(target = "qualificationId", expression = "java(qual.getQualificationId())")
    @Mapping(target = "name", expression = "java(qual.getName())")
    @Mapping(target = "institution", expression = "java(qual.getInstitution())")
    @Mapping(target = "yearObtained", expression = "java(qual.getYearObtained())")
    @Mapping(target = "certificationNumber", expression = "java(qual.getCertificationNumber())")
    @Mapping(target = "issuingAuthority", expression = "java(qual.getIssuingAuthority())")
    @Mapping(target = "description", expression = "java(qual.getDescription())")
    QualificationResponse toResponse(Qualification qual);

    @Mapping(target = "specializationId", expression = "java(spec.getSpecializationId())")
    @Mapping(target = "name", expression = "java(spec.getName())")
    @Mapping(target = "description", expression = "java(spec.getDescription())")
    SpecializationResponse toResponse(Specialization spec);
}
