package com.swp08.dpss.service.impls.consultant;

import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.entity.consultant.Qualification;
import com.swp08.dpss.entity.consultant.Specialization;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.consultant.AvailabilityRepository;
import com.swp08.dpss.repository.consultant.ConsultantRepository;
import com.swp08.dpss.repository.consultant.QualificationRepository;
import com.swp08.dpss.repository.consultant.SpecializationRepository;
import com.swp08.dpss.service.interfaces.consultant.ConsultantService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultantServiceImpl implements ConsultantService {

    private final UserRepository userRepository;
    private final ConsultantRepository consultantRepository;
    private final AvailabilityRepository availabilityRepository;
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;

    //TODO: Change List<ConsultantResponse> instead of List<Consultant>
    @Override
    public List<Consultant> findAll() {
        return consultantRepository.findAll();
    }

    //TODO: use ConsultantCreationRequest instead of Consultant
    @Transactional
    @Override
    public Consultant createNewConsultant(Long userId, Consultant consultant, Set<Availability> availabilities, Set<Specialization> specializations, Qualification qualification) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (!user.getRole().equals(Roles.CONSULTANT)) {
            throw new IllegalArgumentException("User with ID: " + userId + " is not a consultant");
        }

        // Avoid duplicate Consultant (due to @MapsId)
        if (consultantRepository.existsById(userId)) {
            throw new IllegalStateException("Consultant already exists for user ID: " + userId);
        }

        // Set consultant ID to match user ID (due to @MapsId)
        consultant.setUser(user);

        // Set relationships
        Set<Availability> managedAvail = availabilities.stream()
                .map(availabilityRepository::save)
                .collect(Collectors.toSet());

        Set<Specialization> managedSpecs = specializations.stream()
                .map(specializationRepository::save)
                .collect(Collectors.toSet());

        consultant.getTimeSlots().addAll(managedAvail);
        consultant.getSpecializationSet().addAll(managedSpecs);

        qualification.setConsultant(consultant);
        consultant.getQualificationSet().add(qualification);

        // Save consultant (cascades to qualification due to CascadeType.ALL)
        return consultantRepository.save(consultant);
    }

    @Override
    public String saveProfilePicture(Long consultantId, MultipartFile profilePicture) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found with id: " + consultantId));
        if (!isValidPicture(profilePicture)) {
            throw new IllegalArgumentException("Invalid file type. Only JPG/PNG under 5MB are allowed. Please upload a valid image file.");
        }
        String filename = "consultant_" + consultantId + "_" + System.currentTimeMillis() + getFileExtension(profilePicture);
        Path filePath = Paths.get("uploads/profile-pictures/" + filename);
        try {
            // Resize image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(profilePicture.getInputStream())
                    .size(200, 200) // Resize to 200x200
                    .outputFormat("jpg") // Output as jpg
                    .outputQuality(0.8) // Set output quality to 80%
                    .toOutputStream(outputStream); // Write to output stream

            // Create parent directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Write the resized image to the file
            Files.write(filePath, outputStream.toByteArray());

            // Save public relative path (what frontend uses)
            String relativePath = "uploads/profile-pictures/" + filename;
            consultant.setProfilePicture(relativePath);
            consultantRepository.save(consultant);
            return relativePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + filename, e);
        }
    }

    // Check if file is an image and its size is less than 5MB
    // NOTE: You can change the maximum size limit as needed.
    public boolean isValidPicture(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/jpeg") || contentType.startsWith("image/png")
                && (file.getSize() <= 5 * 1024 * 1024); // Max 5MB
    }

    // Get file extension from file name
    // Check if file name contains extension
    // If yes, get the extension
    // If not, set the default extension to jpg
    // NOTE: You can set the default extension to any other format you want.
    // For example, if you want to allow only png and jpg files, then set the default extension to png.
    // If you want to allow only jpg files, then set the default extension to jpg.
    // If you want to allow only png files, then set the default extension to png.
    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return "jpg";
    }
}
