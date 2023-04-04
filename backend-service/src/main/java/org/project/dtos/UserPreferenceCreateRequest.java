package org.project.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private Boolean isTermAccepted;
    @NotNull
    private Set<Long> selectorIds;
}
