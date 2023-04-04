package org.project.v1.controllers;

import lombok.AllArgsConstructor;
import org.project.dtos.UISelectorDTO;
import org.project.v1.services.IUISelectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ui-builder")
@AllArgsConstructor
public class UIBuilderController {

    private final IUISelectorService selectorService;

    @GetMapping("/selectors")
    public List<UISelectorDTO> all() {
        return selectorService.all();
    }
}
