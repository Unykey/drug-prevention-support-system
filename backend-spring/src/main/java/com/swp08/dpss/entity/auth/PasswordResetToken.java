package com.swp08.dpss.entity.auth;

import com.swp08.dpss.entity.client.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
public class PasswordResetToken {
    @Id
    private String token;

    @OneToOne
    User user;

    private LocalDateTime expiryTime;
}
