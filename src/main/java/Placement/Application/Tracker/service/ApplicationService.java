package Placement.Application.Tracker.service;

import Placement.Application.Tracker.dto.ApplicationDTO;
import Placement.Application.Tracker.entity.Application;
import Placement.Application.Tracker.exception.ResourceNotFoundException;
import Placement.Application.Tracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // Convert DTO to Entity
    private Application convertToEntity(ApplicationDTO dto) {

        Application application = new Application();

        application.setCompanyName(dto.getCompanyName());
        application.setRole(dto.getRole());
        application.setStatus(dto.getStatus());
        application.setApplicationDate(dto.getApplicationDate());
        application.setLocation(dto.getLocation());

        return application;
    }

    // Convert Entity to DTO
    private ApplicationDTO convertToDTO(Application application) {

    ApplicationDTO dto = new ApplicationDTO();

    dto.setId(application.getId());
    dto.setCompanyName(application.getCompanyName());
        dto.setRole(application.getRole());
        dto.setStatus(application.getStatus());
        dto.setApplicationDate(application.getApplicationDate());
        dto.setLocation(application.getLocation());

        return dto;
    }

    // CREATE
    public ApplicationDTO createApplication(ApplicationDTO dto) {

        Application application = convertToEntity(dto);

        Application savedApplication =
                applicationRepository.save(application);

        return convertToDTO(savedApplication);
    }

    // READ ALL
    public List<ApplicationDTO> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // READ ONE
    public Optional<ApplicationDTO> getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .map(this::convertToDTO);
    }

    // UPDATE
    public ApplicationDTO updateApplication(
            Long id,
            ApplicationDTO dto) {

        Application existingApplication =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found with id: " + id));

        existingApplication.setCompanyName(dto.getCompanyName());
        existingApplication.setRole(dto.getRole());
        existingApplication.setStatus(dto.getStatus());
        existingApplication.setApplicationDate(dto.getApplicationDate());
        existingApplication.setLocation(dto.getLocation());

        Application updatedApplication =
                applicationRepository.save(existingApplication);

        return convertToDTO(updatedApplication);
    }

    // DELETE
    public void deleteApplication(Long id) {

        if (!applicationRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Application not found with id: " + id);
        }

        applicationRepository.deleteById(id);
    }
}