package org.project.v1.services;

import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;

public interface IUserPreferenceService {
    UserPreferenceResponse create(UserPreferenceCreateRequest createRequest);
     UserPreferenceResponse update(Long id, UserPreferenceUpdateRequest updateRequest);

     UserPreferenceResponse getById(Long id);
}
