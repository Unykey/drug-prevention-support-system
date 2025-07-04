package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.LoginRequest;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    /**
     * Basic login.
     * WARNING: Insecure - plain text password comparison.
     *
     * @param loginRequest Contains email and password.
     * @return User object if login successful, null otherwise.
     */
    @Override
    public User login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //DANGER: PLAIN TEXT PASSWORD COMPARISON
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return user; // Login successful
            }
        }
        return null; // Login failed (user not found or password incorrect)
    }
}
