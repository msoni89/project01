package org.project.v2.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.project.exceptions.NotFoundException;
import org.project.exceptions.AlreadyExistException;
import org.project.mapper.UISelectorMapper;
import org.project.models.UserPreference;
import org.project.repositories.UISelectorRepository;
import org.project.repositories.UserPreferenceRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.function.ServerResponse.*;

@Component
@AllArgsConstructor
@Slf4j
public class UserPreferenceHandler {

    private final UserPreferenceRepository preferenceRepository;
    private final UISelectorRepository selectorRepository;
    private final UISelectorMapper selectorMapper;

    @SneakyThrows
    public ServerResponse create(ServerRequest req) {

        var body = req.body(UserPreferenceCreateRequest.class);
        log.info(String.format("Inside create method, Request received with body %s", body));

        var preference = preferenceRepository.findByName(body.getName());
        if (preference.isPresent()) {
            log.error(String.format("User preference already exist with name %S", body.getName()));
            throw new AlreadyExistException(String.format("User preference already exist with name %S", body.getName()));
        }

        var selectors = selectorRepository.findAllById(body.getSelectorIds());
        if (selectors.isEmpty()) {
            log.error("Selectors does not exist with given ids");
            throw new NotFoundException("Selectors does not exist with given ids");
        }

        var persistedObject = this.preferenceRepository.save(
                UserPreference.builder()
                        .name(body.getName())
                        .selectors(new HashSet<>(selectors))
                        .isTermAccepted(body.getIsTermAccepted())
                        .createdAt(Timestamp.from(Instant.now())).updatedAt(Timestamp.from(Instant.now()))
                        .build());

        log.error(String.format("resource created with id %d", persistedObject.getId()));
        return created(URI.create("/preferences/" + persistedObject.getId())).body(persistedObject);
    }

    public ServerResponse getById(ServerRequest req) {
        Long pathId = Long.valueOf(req.pathVariable("id"));
        log.info(String.format("Inside get method %d", pathId));
        return this.preferenceRepository.findById(pathId)
                .map(userPreference -> UserPreferenceResponse.builder().
                        id(userPreference.getId())
                        .name(userPreference.getName())
                        .createdAt(userPreference.getCreatedAt())
                        .updatedAt(userPreference.getUpdatedAt())
                        .isTermAccepted(userPreference.getIsTermAccepted())
                        .selectors(userPreference.getSelectors().stream().map(selectorMapper::convert).collect(Collectors.toSet()))
                        .build())
                .map(post -> ok().body(post))
                .orElse(notFound().build());
    }

    @SneakyThrows
    public ServerResponse update(ServerRequest req) {
        var body = req.body(UserPreferenceUpdateRequest.class);
        log.info(String.format("Inside update method, Request received with body %s", body));

        var selectors = selectorRepository.findAllById(body.getSelectorIds());
        if (selectors.isEmpty()) {
            log.error("Selectors does not exist with given ids");
            throw new NotFoundException("No Selectors found on given ids");
        }

        return this.preferenceRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        userPreference -> {
                            userPreference.setSelectors(new HashSet<>(selectors));
                            userPreference.setName(body.getName());
                            userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
                            userPreference.setCreatedAt(Timestamp.from(Instant.now()));
                            return userPreference;
                        }
                )
                .map(post -> this.preferenceRepository.save(post))
                .map(post -> noContent().build())
                .orElse(notFound().build());
    }
}
