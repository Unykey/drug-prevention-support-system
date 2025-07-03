package com.swp08.dpss.mapper;

import com.swp08.dpss.dto.requests.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testToEntity() {
        // Arrange
        UserCreationRequest request = new UserCreationRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("123456789");
        request.setPassword("test123");
        request.setGender(Genders.MALE);
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));

        // Act
        User user = userMapper.toEntity(request, passwordEncoder);

        // Assert
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertTrue(passwordEncoder.matches("test123", user.getPassword())); // Password is encoded
        assertEquals(Genders.MALE, user.getGender());
        assertEquals(LocalDate.of(2000, 1, 1), user.getDateOfBirth());
        assertEquals(Roles.MEMBER, user.getRole());
        assertEquals(User_Status.PENDING, user.getStatus());
        assertTrue(user.getGuardians().isEmpty()); // Guardians are not mapped to the User entity yet
    }

    public void AdminTestToEntity() {
        // Arrange
        AdminUserCreationRequest request = new AdminUserCreationRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("123456789");
        request.setPassword("test123");
        request.setGender(Genders.MALE);
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setRole(Roles.ADMIN);
        request.setStatus(User_Status.VERIFIED);

        // Act
        User user = userMapper.toEntity(request, passwordEncoder);

        // Assert
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertTrue(passwordEncoder.matches("test123", user.getPassword())); // Password is encoded
        assertEquals(Genders.MALE, user.getGender());
        assertEquals(LocalDate.of(2000, 1, 1), user.getDateOfBirth());
        assertEquals(Roles.ADMIN, user.getRole());
        assertEquals(User_Status.VERIFIED, user.getStatus());
        assertTrue(user.getGuardians().isEmpty()); // Guardians are not mapped to the User entity yet
    }


    @Test
    void testToResponse() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPhone("123456789");
        user.setGuardians(Collections.emptyList());

        UserResponse response = userMapper.toResponse(user);

        assertEquals(1L, response.getUserId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("123456789", response.getPhone());
        assertNotNull(response.getGuardianIds());
        assertTrue(response.getGuardianIds().isEmpty());
    }

    @Test
    public void testToUserResponseList() {
        // Arrange
        // Create 3 User objects with different properties and add them to a List
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        user1.setGender(Genders.MALE);
        user1.setDateOfBirth(LocalDate.of(2000, 1, 1));
        user1.setEmail("User 1");
        user1.setPhone("123456789");
        user1.setGuardians(Collections.emptyList());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        user2.setGender(Genders.FEMALE);
        user2.setDateOfBirth(LocalDate.of(2001, 1, 1));
        user2.setEmail("User 2");
        user2.setPhone("987654321");
        user2.setGuardians(Collections.emptyList());

        // Create a User object with a Guardian
        User user3 = new User();
        user3.setId(3L);
        user3.setName("User 3");
        user3.setGender(Genders.PREFER_NOT_TO_SAY);
        user3.setDateOfBirth(LocalDate.of(2002, 1, 1));
        user3.setEmail("User 3");
        user3.setPhone("543219876");

        // Create a List contain user3
        List<User> list_user3 = new ArrayList<>();
        list_user3.add(user3);

        // Create a Guardian object and add it to the List of Guardians for user3
        Guardian guardian = new Guardian();
        guardian.setGuardianId(1L);
        guardian.setGuardianName("Guardian 1");
        guardian.setGuardianEmail("Guardian email 1");
        guardian.setGuardianPhone("Guardian phone 1");
        guardian.setUser(list_user3);

        // Add the Guardian object to the List of Guardians for user3
        user3.addGuardian(guardian);

        // Act
        // Convert the List of User objects to a List of UserResponse objects and store them in a new List
        List<User> userList = List.of(user1, user2, user3);
        // Convert the List of User objects to a List of UserResponse objects and store them in a new List
        List<UserResponse> userResponseList = userMapper.toUserResponseList(userList);

        // Assert
        // Size of List should be 3
        assertEquals(3, userResponseList.size());

        //Check the first element
        assertEquals(1L, userResponseList.get(0).getUserId());
        assertEquals("User 1", userResponseList.get(0).getName());
        assertEquals(Genders.MALE, userResponseList.get(0).getGender());
        assertEquals(LocalDate.of(2000, 1, 1), userResponseList.get(0).getDateOfBirth());
        assertEquals("User 1", userResponseList.get(0).getEmail());
        assertEquals("123456789", userResponseList.get(0).getPhone());
        assertNotNull(userResponseList.get(0).getGuardianIds());
        assertTrue(userResponseList.get(0).getGuardianIds().isEmpty());

        //Check the second element
        assertEquals(2L, userResponseList.get(1).getUserId());
        assertEquals("User 2", userResponseList.get(1).getName());
        assertEquals(Genders.FEMALE, userResponseList.get(1).getGender());
        assertEquals(LocalDate.of(2001, 1, 1), userResponseList.get(1).getDateOfBirth());
        assertEquals("User 2", userResponseList.get(1).getEmail());
        assertEquals("987654321", userResponseList.get(1).getPhone());
        assertNotNull(userResponseList.get(1).getGuardianIds());
        assertTrue(userResponseList.get(1).getGuardianIds().isEmpty());

        //Check the third element
        assertEquals(3L, userResponseList.get(2).getUserId());
        assertEquals("User 3", userResponseList.get(2).getName());
        assertEquals(Genders.PREFER_NOT_TO_SAY, userResponseList.get(2).getGender());
        assertEquals(LocalDate.of(2002, 1, 1), userResponseList.get(2).getDateOfBirth());
        assertEquals("User 3", userResponseList.get(2).getEmail());
        assertEquals("543219876", userResponseList.get(2).getPhone());
        assertNotNull(userResponseList.get(2).getGuardianIds());
        assertFalse(userResponseList.get(2).getGuardianIds().isEmpty());
        assertEquals(1, userResponseList.get(2).getGuardianIds().size());
    }
}
