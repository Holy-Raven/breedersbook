package ru.codesquad.showPart.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.dto.ShowPartNewDto;
import ru.codesquad.showPart.dto.ShowPartUpdateDto;
import ru.codesquad.showPart.model.ShowPart;
import ru.codesquad.showPart.repository.ShowPartRepository;
import ru.codesquad.util.UnionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.codesquad.showPart.mapper.ShowPartMapper.returnFullDto;
import static ru.codesquad.util.TestFactory.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ShowPartServiceTest {

    @Mock
    private ShowPartRepository repository;

    @Mock
    private UnionService unionService;

    @InjectMocks
    private ShowPartServiceImpl service;

    private ShowPartFullDto fullDto;
    private ShowPart showPart;

    @BeforeEach
    void setup() {
        showPart = makeFilledShow(1, 1);
        fullDto = returnFullDto(showPart);
    }

    @Test
    void shouldGetById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(showPart));
        ShowPartFullDto showGot = service.getById(1L);
        assertNotNull(showGot);
        assertEquals(fullDto, showGot);
    }

    @Test
    void shouldAdd() {
        ShowPartNewDto newDto = makeNewShowDto(1);
        Pet pet = makeFilledCat(1);
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(unionService.getPetOrNotFound(anyLong())).thenReturn(pet);
        when(repository.save(any())).thenReturn(showPart);
        ShowPartFullDto addedShow = service.add(1L, 1L, newDto);
        assertNotNull(addedShow);
        assertEquals(fullDto, addedShow);
    }

    @Test
    void shouldDelete() {
        doNothing().when(repository).deleteById(anyLong());
        service.delete(1);
        verify(repository).deleteById(anyLong());
    }

    @Test
    void shouldGetByUserById() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(showPart));
        ShowPartFullDto showGot = service.getByUserById(1L, 1L);
        assertNotNull(showGot);
        assertEquals(fullDto, showGot);
    }

    @Test
    void shouldDeleteByUser() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(showPart));
        doNothing().when(repository).deleteById(anyLong());
        service.deleteByUser(1L, 1);
    }

    @Test
    void shouldGetEmptyListByPetId() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        //EmptyList
        when(repository.findShowsByPetId(anyLong(), any())).thenReturn(Page.empty());
        List<ShowPartFullDto> dtos = service.getByPetId(1L, 1, false, 0, 10);
        assertNotNull(dtos);
        assertEquals(0, dtos.size());
    }

    @Test
    void shouldGetSingleListByPetId() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        //EmptyList
        when(repository.findShowsByPetId(anyLong(), any())).thenReturn(new PageImpl<>(List.of(showPart)));
        List<ShowPartFullDto> dtos = service.getByPetId(1L, 1, false, 0, 10);
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(fullDto, dtos.get(0));
    }

    @Test
    void shouldUpdate() {
        ShowPartUpdateDto updateDto = makeUpdateDto(1);
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(showPart));
        when(repository.save(any())).thenReturn(showPart);
        ShowPartFullDto updatedShow = service.update(1L, 1L, updateDto);
        assertNotNull(updatedShow);
        assertEquals(fullDto, updatedShow);
    }
}