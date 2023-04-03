package org.project.mapper;

import org.project.dtos.UISelectorDTO;
import org.project.models.UISelector;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class UISelectorMapper {

    public UISelectorDTO convert(UISelector selector) {
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
