package org.project.repositories;

import org.project.models.UISelector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UISelectorRepository extends JpaRepository<UISelector, Long> {
    List<UISelector> findByIsParentTrueOrderByIdAsc();
}
