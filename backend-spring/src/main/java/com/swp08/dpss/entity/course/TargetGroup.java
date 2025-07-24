package com.swp08.dpss.entity.course;

import com.swp08.dpss.enums.TargetGroupName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TargetGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
        private TargetGroupName targetGroupName;

    private String description;
}
