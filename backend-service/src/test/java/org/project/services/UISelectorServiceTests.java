package org.project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.dtos.UISelectorDTO;
import org.project.mapper.UISelectorMapper;
import org.project.models.UISelector;
import org.project.repositories.UISelectorRepository;
import org.project.v1.services.impl.UISelectorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UISelectorServiceTests {

    @Mock
    private UISelectorRepository selectorRepository;

    @Mock
    @Autowired
    private UISelectorMapper mapper;

    @InjectMocks
    private UISelectorService selectorService;

    @Test
    void getAllUISelectors() {
//        Service
//          Engineering
//          Tourism
//          Translation services
//          Transport and Logistics
//              Air
//              Rail
//              Road
//              Water

        List<UISelector> selectors = new ArrayList<>();

        selectors.add(UISelector.builder().title("Service").selectors(
                Set.of(
                        UISelector.builder().selectors(Collections.emptySet()).title("Engineering").id(2L).build(),
                        UISelector.builder().selectors(Collections.emptySet()).title("Tourism").id(3L).build(),
                        UISelector.builder().selectors(Collections.emptySet()).title("Translation services").id(4L).build(),
                        UISelector.builder().selectors(Set.of((UISelector.builder().id(6L).title("Air").selectors(Collections.emptySet()).build()),
                                UISelector.builder().id(7L).title("Rail").selectors(Collections.emptySet()).build(),
                                UISelector.builder().id(8L).title("Road").selectors(Collections.emptySet()).build(),
                                UISelector.builder().id(9L).title("Water").selectors(Collections.emptySet()).build())).title("Transport and Logistics").id(5L).build()
                )
        ).id(1L).build());

        when(selectorRepository.findByIsParentTrueOrderByIdAsc()).thenReturn(selectors);
        when(mapper.convert(Mockito.any(UISelector.class))).thenReturn(
                UISelectorDTO.builder().title("Service").selectors(
                        List.of(
                                UISelectorDTO.builder().selectors(Collections.emptyList()).title("Engineering").id(2L).build(),
                                UISelectorDTO.builder().selectors(Collections.emptyList()).title("Tourism").id(3L).build(),
                                UISelectorDTO.builder().selectors(Collections.emptyList()).title("Translation services").id(4L).build(),
                                UISelectorDTO.builder().selectors(List.of((UISelectorDTO.builder().id(6L).title("Air").selectors(Collections.emptyList()).build()),
                                        UISelectorDTO.builder().id(7L).title("Rail").selectors(Collections.emptyList()).build(),
                                        UISelectorDTO.builder().id(8L).title("Road").selectors(Collections.emptyList()).build(),
                                        UISelectorDTO.builder().id(9L).title("Water").selectors(Collections.emptyList()).build())).title("Transport and Logistics").id(5L).build()
                        )
                ).id(1L).build()
        );

        List<UISelectorDTO> selectorDTOList = selectorService.all();

        assertEquals(1L, selectorDTOList.size());
        UISelectorDTO parentElement = selectorDTOList.get(0);
        assertEquals("Service", parentElement.getTitle());
        assertEquals(4L, parentElement.getSelectors().size());
        assertEquals("Engineering", parentElement.getSelectors().get(0).getTitle());
        assertEquals("Tourism", parentElement.getSelectors().get(1).getTitle());
        assertEquals("Translation services", parentElement.getSelectors().get(2).getTitle());
        assertEquals("Transport and Logistics", parentElement.getSelectors().get(3).getTitle());
        assertEquals(4L, parentElement.getSelectors().get(3).getSelectors().size());
        assertEquals("Air", parentElement.getSelectors().get(3).getSelectors().get(0).getTitle());
        assertEquals("Rail", parentElement.getSelectors().get(3).getSelectors().get(1).getTitle());
        assertEquals("Road", parentElement.getSelectors().get(3).getSelectors().get(2).getTitle());
        assertEquals("Water", parentElement.getSelectors().get(3).getSelectors().get(3).getTitle());
    }
}
