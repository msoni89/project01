package org.project.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserPreferenceResponse {

    private Long id;
    @NotNull
    private String name;

    @NotNull
    private Boolean isTermAccepted;
    @NotNull
    private Set<UISelectorDTO> selectors;

    Timestamp createdAt;
    Timestamp updatedAt;

}
