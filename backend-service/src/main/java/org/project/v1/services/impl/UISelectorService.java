package org.project.v1.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.dtos.UISelectorDTO;
import org.project.mapper.UISelectorMapper;
import org.project.repositories.UISelectorRepository;
import org.project.v1.services.IUISelectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UISelectorService implements IUISelectorService {

    private final UISelectorRepository selectorRepository;

    private  final UISelectorMapper selectorMapper;

    @Override
    public List<UISelectorDTO> all() {
        log.info("Inside all method");
        return this.selectorRepository.findByIsParentTrueOrderByIdAsc().stream().map(selectorMapper::convert).toList();
    }


}
