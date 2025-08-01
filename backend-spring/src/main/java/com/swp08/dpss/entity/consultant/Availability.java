package com.swp08.dpss.entity.consultant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "availability")
@Getter
@Setter
@NoArgsConstructor
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityId;

    @ManyToMany(mappedBy = "timeSlots")
    private Set<Consultant> consultant;

    private LocalDate date; //e.g. 2021-09-01, etc.
    private LocalTime startTime; //e.g. 10:00, etc.
    private LocalTime endTime; //e.g. 11:00, etc.
    private boolean isAvailable; //true if the consultant is available, false otherwise

    public Availability(LocalDate date, LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }
}
