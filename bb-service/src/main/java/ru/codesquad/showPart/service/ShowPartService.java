package ru.codesquad.showPart.service;

import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.dto.ShowPartNewDto;
import ru.codesquad.showPart.dto.ShowPartUpdateDto;

import java.util.List;

public interface ShowPartService {
    ShowPartFullDto getById(long showId);

    ShowPartFullDto add(Long userId, Long petId, ShowPartNewDto showPartNewDto);

    boolean delete(long showId);

    ShowPartFullDto getByUserById(Long userId, long showId);

    boolean deleteByUser(Long userId, long showId);

    List<ShowPartFullDto> getByPetId(Long userId, long petId, Boolean asc, Integer from, Integer size);

    ShowPartFullDto update(Long userId, Long showId, ShowPartUpdateDto showPartUpdateDto);
}
