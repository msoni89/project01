package org.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SELECTORS_TBL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UISelector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Boolean isParent;

    @OneToMany
    @JoinColumn(name = "parent_selector_id", referencedColumnName = "id")
    private Set<UISelector> selectors = new HashSet<>();
}