package com.swp08.dpss.entity.consultant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "qualification")
@Getter
@Setter
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qualificationId;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    @Column(nullable = false)
    private String name; //e.g. MD, PhD, etc.

    @Column(nullable = false)
    private String institution; //e.g. University of Hong Kong, etc.

    private Integer yearObtained; //e.g. 2010, 2014, etc.

    private String certificationNumber; //e.g. 123456789, A2341, etc.

    private String issuingAuthority; //e.g. Hanoi Medical University, etc.

    private String description; //e.g. General Medicine, Addiction Psychology, etc.

}
