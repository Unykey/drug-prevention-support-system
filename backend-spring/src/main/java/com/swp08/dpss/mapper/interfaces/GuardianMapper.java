package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.GuardianResponse;
import com.swp08.dpss.entity.client.Guardian;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuardianMapper {
    List<GuardianResponse> toGuardianResponseList(List<Guardian> guardianList);
}
