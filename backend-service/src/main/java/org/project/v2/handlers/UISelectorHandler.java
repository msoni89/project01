package org.project.v2.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.dtos.UISelectorDTO;
import org.project.models.UISelector;
import org.project.repositories.UISelectorRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Comparator;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@AllArgsConstructor
@Component
@Slf4j
public class UISelectorHandler {

    private UISelectorRepository repository;

    public ServerResponse all(ServerRequest request) {
        return ok().body(this.repository.findByIsParentTrueOrderByIdAsc().stream().map(this::convert).toList());
    }

    UISelectorDTO convert(UISelector selector) {
        return UISelectorDTO.builder()
                .title(selector.getTitle())
                .id(selector.getId())
                .selectors(selector.getSelectors()
                        .stream().map(this::convert)
                        .sorted(Comparator.comparing(UISelectorDTO::getId))
                        .toList())
                .build();
    }
}
