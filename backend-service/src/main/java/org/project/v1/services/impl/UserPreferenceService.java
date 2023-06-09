package org.project.v1.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.project.exceptions.AlreadyExistException;
import org.project.exceptions.NotFoundException;
import org.project.mapper.UISelectorMapper;
import org.project.models.UISelector;
import org.project.models.UserPreference;
import org.project.repositories.UISelectorRepository;
import org.project.repositories.UserPreferenceRepository;
import org.project.v1.services.IUserPreferenceService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserPreferenceService implements IUserPreferenceService {

    private final UserPreferenceRepository preferenceRepository;

    private final UISelectorMapper selectorMapper;

    private final UISelectorRepository selectorRepository;

    @Override
    public UserPreferenceResponse create(UserPreferenceCreateRequest createRequest) {
        log.info(String.format("Inside update method, Request received with body %s", createRequest));
        var preference = preferenceRepository.findByName(createRequest.getName());
        if (preference.isPresent()) {
            log.error(String.format("User preference already exist with name %S", createRequest.getName()));
            throw new AlreadyExistException(String.format("User preference already exist with name %S", createRequest.getName()));
        }


        List<UISelector> selectors = selectorRepository.findAllById(new HashSet<>(createRequest.getSelectorIds()));
        if (selectors.isEmpty()) {
            log.error("Selectors does not exist with given ids");
            throw new NotFoundException("No Selectors found on given ids");
        }

        UserPreference userPreference = UserPreference.builder()
                .name(createRequest.getName())
                .createdAt(Timestamp.from(Instant.now()))
                .isTermAccepted(createRequest.getIsTermAccepted()).selectors(new HashSet<>(selectors))
                .updatedAt(Timestamp.from(Instant.now())).build();

        UserPreference createdUserPreference = preferenceRepository.save(userPreference);
        return UserPreferenceResponse.builder()
                .name(createdUserPreference.getName())
                .isTermAccepted(createdUserPreference.getIsTermAccepted())
                .createdAt(createdUserPreference.getCreatedAt())
                .updatedAt(createdUserPreference.getUpdatedAt())
                .selectors(createdUserPreference.getSelectors().stream().map(selectorMapper::convert).collect(Collectors.toSet()))
                .id(createdUserPreference.getId()).build();
    }

    @Override
    public UserPreferenceResponse update(Long id, UserPreferenceUpdateRequest updateRequest) {
        log.info(String.format("Inside update method, Request received with body %s", updateRequest));

        UserPreference preference = preferenceRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User preference does not exist with id %d", id)));

        List<UISelector> selectors = selectorRepository.findAllById(updateRequest.getSelectorIds());
        if (selectors.isEmpty()) {
            log.error("Selectors does not exist with given ids");
            throw new NotFoundException("No Selectors found on given ids");
        }

        preference.setSelectors(new HashSet<>(selectors));
        preference.setName(updateRequest.getName());
        preference.setUpdatedAt(Timestamp.from(Instant.now()));
        UserPreference savedResult = preferenceRepository.save(preference);
        return UserPreferenceResponse.builder()
                .isTermAccepted(savedResult.getIsTermAccepted())
                .name(savedResult.getName())
                .updatedAt(savedResult.getUpdatedAt())
                .selectors(savedResult.getSelectors().stream().map(selectorMapper::convert).collect(Collectors.toSet()))
                .id(savedResult.getId()).build();
    }

    @Override
    public UserPreferenceResponse getById(Long id) {
        log.info(String.format("Inside get method %d", id));
        UserPreference findResult = preferenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User preference does not exist with id %d", id)));
        return UserPreferenceResponse.builder()
                .name(findResult.getName())
                .isTermAccepted(findResult.getIsTermAccepted())
                .createdAt(findResult.getCreatedAt())
                .updatedAt(findResult.getUpdatedAt())
                .selectors(findResult.getSelectors().stream().map(selectorMapper::convert).collect(Collectors.toSet()))
                .id(findResult.getId()).build();
    }
}
