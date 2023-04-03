package org.project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.dtos.UISelectorDTO;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.project.exceptions.NotFoundException;
import org.project.exceptions.UserPreferenceAlreadyExist;
import org.project.mapper.UISelectorMapper;
import org.project.models.UISelector;
import org.project.models.UserPreference;
import org.project.repositories.UISelectorRepository;
import org.project.repositories.UserPreferenceRepository;
import org.project.v1.services.impl.UserPreferenceService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPreferenceServiceTests {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private UISelectorRepository uiSelectorRepository;

    @Mock
    private UISelectorMapper mapper;

    @InjectMocks
    private UserPreferenceService userPreferenceService;



    @Test
    void testCreateUser() {
        UserPreference userPreference = new UserPreference();
        userPreference.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selector = new UISelector();
        selector.setTitle("Manufacturing");
        selector.setId(1L);
        userPreference.setSelectors(Set.of(selector));

        when(userPreferenceRepository.save(Mockito.any())).thenReturn(userPreference);
        when(uiSelectorRepository.findAllById(Mockito.anyCollection())).thenReturn((List.of(selector)));
        UserPreferenceResponse userPreferenceDTO = userPreferenceService.create(
                UserPreferenceCreateRequest.builder()
                        .selectorIds(Set.of(1L))
                .isTermAccepted(userPreference.getIsTermAccepted())
                .selectorIds(userPreference.getSelectors().stream().map(UISelector::getId).collect(Collectors.toSet())).build());
        assertThat(userPreferenceDTO).isNotNull();
        assertThat(userPreferenceDTO.getName()).isEqualTo("Patrick");
    }

    @Test
    void createUserWithSameName_Throw_AlreadyExistException() {
        UserPreference userPreference = new UserPreference();
        userPreference.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selector = new UISelector();
        selector.setTitle("Manufacturing");
        selector.setId(1L);
        userPreference.setSelectors(Set.of(selector));
        when(userPreferenceRepository.findByName(Mockito.any())).thenReturn(Optional.of(userPreference));
        assertThrows(UserPreferenceAlreadyExist.class, () -> {
            userPreferenceService.create(UserPreferenceCreateRequest.builder()
                    .isTermAccepted(userPreference.getIsTermAccepted())
                    .selectorIds(userPreference.getSelectors().stream().map(UISelector::getId).collect(Collectors.toSet())).build());
        });
    }

    @Test
    void createWithInvalidSelectorId_Throw_SelectorNotFoundException() {
        UserPreference userPreference = new UserPreference();
        userPreference.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selector = new UISelector();
        selector.setTitle("Manufacturing");
        selector.setId(1L);
        userPreference.setSelectors(Set.of(selector));
        when(userPreferenceRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        when(uiSelectorRepository.findAllById(Mockito.anyCollection())).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> {
            userPreferenceService.create(UserPreferenceCreateRequest.builder()
                    .isTermAccepted(userPreference.getIsTermAccepted())
                    .selectorIds(userPreference.getSelectors().stream().map(UISelector::getId).collect(Collectors.toSet())).build());
        });
    }

    @Test
    void update_NonExisting_UserPreference_Throw_UserPreferenceNotFoundException() {
        when(userPreferenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            UserPreferenceUpdateRequest preferenceUpdateRequest = UserPreferenceUpdateRequest.builder().name("Updated Name").selectorIds(Set.of(2L)).build();
            userPreferenceService.update(1L, preferenceUpdateRequest);
        });
    }

    @Test
    void update_NonExisting_Selector_Throw_SelectNotFoundException() {
        UserPreference userPreference = new UserPreference();
        userPreference.setId(1L);
        userPreference.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selector = new UISelector();
        selector.setTitle("Manufacturing");
        selector.setId(1L);
        userPreference.setSelectors(Set.of(selector));
        when(userPreferenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userPreference));
        when(uiSelectorRepository.findAllById(Mockito.anyCollection())).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> {
            UserPreferenceUpdateRequest preferenceUpdateRequest = UserPreferenceUpdateRequest.builder().name("Updated Name").selectorIds(Set.of(30L)).build();
            userPreferenceService.update(1L, preferenceUpdateRequest);
        });
    }

    @Test
    void update() {
        UserPreference userPreference = new UserPreference();
        userPreference.setId(1L);
        userPreference.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selector = new UISelector();
        selector.setTitle("Manufacturing");
        selector.setId(1L);
        userPreference.setSelectors(Set.of(selector));
        when(userPreferenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userPreference));

        UserPreference userPreferenceUpdate = new UserPreference();
        userPreferenceUpdate.setId(1L);
        userPreferenceUpdate.setName("Patrick");
        userPreference.setUpdatedAt(Timestamp.from(Instant.now()));
        userPreference.setCreatedAt(Timestamp.from(Instant.now()));
        UISelector selectorUpdate = new UISelector();
        selectorUpdate.setTitle("Others");
        selectorUpdate.setId(2L);
        selectorUpdate.setSelectors(Collections.emptySet());
        userPreferenceUpdate.setSelectors(Set.of(selectorUpdate));
        when(userPreferenceRepository.save(Mockito.any())).thenReturn(userPreferenceUpdate);
        when(uiSelectorRepository.findAllById(Mockito.anyCollection())).thenReturn((List.of(selectorUpdate)));

        when(mapper.convert(Mockito.any(UISelector.class))).thenReturn(UISelectorDTO.builder().id(2L).title("Others").build());
        UserPreferenceUpdateRequest preferenceUpdateRequest = UserPreferenceUpdateRequest.builder().name("Updated Name").selectorIds(Set.of(2L)).build();
        var updateUserPreferenceDTO = userPreferenceService.update(userPreference.getId(), preferenceUpdateRequest);
        assertEquals(2L, updateUserPreferenceDTO.getSelectors().stream().findFirst().get().getId());
    }
}
