package com.swp08.dpss.dto.responses.consultant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class AvailabilityResponse {
    private final Long availabilityId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final boolean isAvailable;
}
