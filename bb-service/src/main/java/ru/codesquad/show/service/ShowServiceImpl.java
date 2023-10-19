package ru.codesquad.show.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.Breed;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.show.repository.ShowRepository;

import java.util.List;

import static ru.codesquad.show.mapper.ShowMapper.returnFullDto;
import static ru.codesquad.show.mapper.ShowMapper.returnShow;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository repository;

    @Override
    public ShowFullDto getById(long showId) {
        Show show = repository.findById(showId)
                .orElseThrow(() -> new NotFoundException(Breed.class, String.format("Show participation with id %d not found", showId)));
        return returnFullDto(show);
    }

    @Override
    public ShowFullDto add(Long userId, Long petId, ShowNewDto showNewDto) {
        Show show = returnShow(showNewDto);
        show = repository.save(show);
        log.info("Show participation with id {} was added to DB", show.getId());
        return returnFullDto(show);
    }

    @Override
    public void delete(long showId) {
    }

    @Override
    public ShowFullDto getByUserById(Long userId, long showId) {
        return null;
    }

    @Override
    public void deleteByUser(Long userId, long showId) {

    }

    @Override
    public List<ShowFullDto> getByPetId(Long userId, long petId, Boolean asc, Integer from, Integer size) {
        return null;
    }

    @Override
    public ShowFullDto update(Long userId, Long showId, ShowUpdateDto showUpdateDto) {
        return null;
    }
}
