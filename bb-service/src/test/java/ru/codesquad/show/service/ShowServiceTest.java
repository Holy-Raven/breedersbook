package ru.codesquad.show.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.show.repository.ShowRepository;
import ru.codesquad.util.UnionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.codesquad.show.mapper.ShowMapper.returnFullDto;
import static ru.codesquad.util.TestFactory.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ShowServiceTest {

    @Mock
    private ShowRepository repository;

    @Mock
    private UnionService unionService;

    @InjectMocks
    private ShowServiceImpl service;

    private ShowFullDto fullDto;
    private Show show;

    @BeforeEach
    void setup() {
        show = makeFilledShow(1, 1);
        fullDto = returnFullDto(show);
    }

    @Test
    void shouldGetById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(show));
        ShowFullDto showGot = service.getById(1L);
        assertNotNull(showGot);
        assertEquals(fullDto, showGot);
    }

    @Test
    void shouldAdd() {
        ShowNewDto newDto = makeNewShowDto(1);
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.save(any())).thenReturn(show);
        ShowFullDto addedShow = service.add(1L, 1L, newDto);
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
        when(repository.findById(anyLong())).thenReturn(Optional.of(show));
        ShowFullDto showGot = service.getByUserById(1L, 1L);
        assertNotNull(showGot);
        assertEquals(fullDto, showGot);
    }

    @Test
    void shouldDeleteByUser() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(show));
        doNothing().when(repository).deleteById(anyLong());
        service.deleteByUser(1L, 1);
    }

    @Test
    void shouldGetEmptyListByPetId() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        //EmptyList
        when(repository.findShowsByPetId(anyLong(), any())).thenReturn(Page.empty());
        List<ShowFullDto> dtos = service.getByPetId(1L, 1, false, 0, 10);
        assertNotNull(dtos);
        assertEquals(0, dtos.size());
    }

    @Test
    void shouldGetSingleListByPetId() {
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        //EmptyList
        when(repository.findShowsByPetId(anyLong(), any())).thenReturn(new PageImpl<>(List.of(show)));
        List<ShowFullDto> dtos = service.getByPetId(1L, 1, false, 0, 10);
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(fullDto, dtos.get(0));
    }

    @Test
    void shouldUpdate() {
        ShowUpdateDto updateDto = makeUpdateDto(1);
        doNothing().when(unionService).checkOwner(anyLong(), anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(show));
        when(repository.save(any())).thenReturn(show);
        ShowFullDto updatedShow = service.update(1L, 1L, updateDto);
        assertNotNull(updatedShow);
        assertEquals(fullDto, updatedShow);
    }
}