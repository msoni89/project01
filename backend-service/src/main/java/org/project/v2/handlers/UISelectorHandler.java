package org.project.v2.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.mapper.UISelectorMapper;
import org.project.repositories.UISelectorRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@AllArgsConstructor
@Component
@Slf4j
public class UISelectorHandler {

    private UISelectorRepository repository;
    private UISelectorMapper mapper;

    public ServerResponse all(ServerRequest request) {
        log.info("Inside all method");
        return ok().body(this.repository.findByIsParentTrueOrderByIdAsc().stream().map(this.mapper::convert).toList());
    }

}
