package com.swp08.dpss.service.impls;

import com.swp08.dpss.entity.auth.PasswordResetToken;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.auth.PasswordResetTokenRepository;
import com.swp08.dpss.service.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;

    private final PasswordResetTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null || resetToken.getExpiryTime().isBefore(java.time.LocalDateTime.now()) ) {
            return false;
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken); //Delete the token after it has been used to reset the password.
        return true;
    }
}
