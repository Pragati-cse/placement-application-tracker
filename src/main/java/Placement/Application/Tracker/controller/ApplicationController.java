package Placement.Application.Tracker.controller;

import Placement.Application.Tracker.dto.ApplicationDTO;
import Placement.Application.Tracker.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(
            @RequestBody ApplicationDTO applicationDTO) {

        ApplicationDTO createdApplication =
                applicationService.createApplication(applicationDTO);

        return ResponseEntity.ok(createdApplication);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {

        List<ApplicationDTO> applications =
                applicationService.getAllApplications();

        return ResponseEntity.ok(applications);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(
            @PathVariable Long id) {

        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(
            @PathVariable Long id,
            @RequestBody ApplicationDTO applicationDTO) {

        ApplicationDTO updatedApplication =
                applicationService.updateApplication(id, applicationDTO);

        return ResponseEntity.ok(updatedApplication);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}