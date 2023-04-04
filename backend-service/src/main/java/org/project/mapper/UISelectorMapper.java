package org.project.mapper;

import lombok.extern.slf4j.Slf4j;
import org.project.dtos.UISelectorDTO;
import org.project.models.UISelector;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Slf4j
public class UISelectorMapper {

    public UISelectorDTO convert(UISelector selector) {
        log.info("Inside UISelectorMapper::convert method");
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
