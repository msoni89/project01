package org.project.v1.services.impl;

import lombok.AllArgsConstructor;
import org.project.dtos.UISelectorDTO;
import org.project.mapper.UISelectorMapper;
import org.project.repositories.UISelectorRepository;
import org.project.v1.services.IUISelectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UISelectorService implements IUISelectorService {

    private final UISelectorRepository selectorRepository;

    private  final UISelectorMapper selectorMapper;

    @Override
    public List<UISelectorDTO> all() {
        return this.selectorRepository.findByIsParentTrueOrderByIdAsc().stream().map(selectorMapper::convert).toList();
    }


}
