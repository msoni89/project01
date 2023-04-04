package org.project.v2.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.project.exceptions.NotFoundException;
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
public class UserPreferenceHandler {

    private final UserPreferenceRepository preferenceRepository;
    private final UISelectorRepository selectorRepository;
    private final UISelectorMapper selectorMapper;

    @SneakyThrows
    public ServerResponse create(ServerRequest req) {
        var body = req.body(UserPreferenceCreateRequest.class);

        var selectors = new HashSet<>(selectorRepository.findAllById(
                body.getSelectorIds()));

        if (selectors.isEmpty()) {
            throw new NotFoundException("Selectors does not exist with given ids");
        }

        var saved = this.preferenceRepository.save(UserPreference.builder()
                .name(body.getName())
                .selectors(selectors)
                .isTermAccepted(body.getIsTermAccepted())
                .createdAt(Timestamp.from(Instant.now())).updatedAt(Timestamp.from(Instant.now()))
                .build());
        return created(URI.create("/preferences/" + saved.getId())).body(saved);
    }

    public ServerResponse get(ServerRequest req) {
        return this.preferenceRepository.findById(Long.valueOf(req.pathVariable("id")))
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

        return this.preferenceRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        userPreference -> {
                            userPreference.setSelectors(
                                    new HashSet<>(selectorRepository.findAllById(body.getSelectorIds())));
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
