package com.swp08.dpss.service.impls.program;

import com.swp08.dpss.dto.requests.program.ProgramParticipationRequest;
import com.swp08.dpss.dto.requests.program.ProgramRequest;
import com.swp08.dpss.dto.responses.program.ProgramParticipationResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.program.Program;
import com.swp08.dpss.entity.program.ProgramParticipation;
import com.swp08.dpss.entity.program.ProgramParticipationId;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.program.ProgramParticipationRepository;
import com.swp08.dpss.repository.program.ProgramRepository;
import com.swp08.dpss.service.interfaces.program.ProgramService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramParticipationRepository programParticipationRepository;
    private final UserRepository userRepository;

    @Override

    public ProgramParticipationResponse enroll(ProgramParticipationRequest request) {
        Long programId = request.getProgramId();
        Long userId = request.getUserId();
        if (programParticipationRepository.existsById(new ProgramParticipationId(userId, programId))) {
            throw new IllegalStateException("User is already participated in this program");
        }
        Program program = programRepository.findById(programId).orElseThrow(()-> new EntityNotFoundException("Program Not Found with id " + programId));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User not found with id " + userId));

        ProgramParticipation participation = new ProgramParticipation();
        participation.setProgram(program);
        participation.setUser(user);
        participation.setId(new ProgramParticipationId(user.getId(), program.getId()));

        programParticipationRepository.save(participation);
        return toDto(participation);
    }

    @Override
    @Transactional
    public Program addProgram(ProgramRequest request) {
        Program newProgram = new Program();

        newProgram.setTitle(request.getTitle());
        newProgram.setDescription(request.getDescription());
        newProgram.setStart_date(request.getStartDate());
        newProgram.setEnd_date(request.getEndDate());
        newProgram.setHostedBy(request.getHostedBy());
        newProgram.setLocation(request.getLocation());

        return programRepository.save(newProgram);
    }

    public ProgramParticipationResponse toDto(ProgramParticipation participation) {
        ProgramParticipationResponse dto = new ProgramParticipationResponse();
        dto.setUserId(participation.getUser().getId());
        dto.setProgramId(participation.getProgram().getId());
        dto.setJoinedAt(participation.getJoinedAt());
        return dto;
    }
}
