package com.swp08.dpss.service.interfaces.program;

import com.swp08.dpss.dto.requests.program.ProgramParticipationRequest;
import com.swp08.dpss.dto.requests.program.ProgramRequest;
import com.swp08.dpss.dto.responses.program.ProgramParticipationResponse;
import com.swp08.dpss.entity.program.Program;

public interface ProgramService {
    ProgramParticipationResponse enroll(ProgramParticipationRequest request);

    Program addProgram(ProgramRequest program);
}
