package org.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UISelectorDTO {

    private Long id;
    private String title;
    @Builder.Default
    private List<UISelectorDTO> selectors  = new ArrayList<>();

}
