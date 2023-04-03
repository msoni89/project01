package org.project.v1.controllers;

import lombok.AllArgsConstructor;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.project.v1.services.impl.UserPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/user-preferences")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService preferenceService;

    @PostMapping
    private ResponseEntity<UserPreferenceResponse> create(@RequestBody UserPreferenceCreateRequest createRequest) {
        UserPreferenceResponse created = preferenceService.create(createRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<UserPreferenceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(preferenceService.getById(id));
    }

    @PutMapping("/id/{id}")
    private ResponseEntity<UserPreferenceResponse> update(@PathVariable("id") Long id, @RequestBody UserPreferenceUpdateRequest updateRequest) {
        return ResponseEntity.ok().body(preferenceService.update(id, updateRequest));
    }
}
