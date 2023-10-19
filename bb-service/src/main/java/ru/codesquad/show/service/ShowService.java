package ru.codesquad.show.service;

import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;

import java.util.List;

public interface ShowService {
    ShowFullDto getById(long showId);

    ShowFullDto add(Long userId, Long petId, ShowNewDto showNewDto);

    void delete(long showId);

    ShowFullDto getByUserById(Long userId, long showId);

    void deleteByUser(Long userId, long showId);

    List<ShowFullDto> getByPetId(Long userId, long petId, Boolean asc, Integer from, Integer size);

    ShowFullDto update(Long userId, Long showId, ShowUpdateDto showUpdateDto);
}
