package org.project.v1.services;

import org.project.dtos.UISelectorDTO;
import org.project.models.UISelector;

import java.util.List;

public interface IUISelectorService {
    List<UISelectorDTO> all();
}


