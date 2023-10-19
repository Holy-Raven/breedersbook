package ru.codesquad.show.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.Breed;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.show.repository.ShowRepository;
import ru.codesquad.util.UnionService;

import java.util.List;

import static ru.codesquad.show.mapper.ShowMapper.returnFullDto;
import static ru.codesquad.show.mapper.ShowMapper.returnShow;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private static final Sort SORT_DESC = Sort.by(Sort.Direction.DESC, "date");
    private static final Sort SORT_ASC = Sort.by(Sort.Direction.ASC, "date");

    private final ShowRepository repository;
    private final UnionService unionService;

    @Override
    public ShowFullDto getById(long showId) {
        Show show = getOrNotFound(showId);
        return returnFullDto(show);
    }

    @Override
    public ShowFullDto add(Long userId, Long petId, ShowNewDto showNewDto) {
        unionService.checkOwner(userId, petId);
        Show show = returnShow(showNewDto, petId);
        show = repository.save(show);
        log.info("Show participation with id {} was added to DB", show.getId());
        return returnFullDto(show);
    }

    @Override
    public void delete(long showId) {
        log.info("Show with id {} was deleted by admin", showId);
        repository.deleteById(showId);
    }

    @Override
    public ShowFullDto getByUserById(Long userId, long showId) {
        Show show = getOrNotFound(showId);
        unionService.checkOwner(userId, show.getPetId());
        return returnFullDto(show);
    }

    @Override
    public void deleteByUser(Long userId, long showId) {
        Show show = getOrNotFound(showId);
        unionService.checkOwner(userId, show.getPetId());
        log.info("Show with id {} was deleted by user with id {}", showId, userId);
        repository.deleteById(showId);
    }

    @Override
    public List<ShowFullDto> getByPetId(Long userId, long petId, Boolean asc, Integer from, Integer size) {
        unionService.checkOwner(userId, petId);
        Sort sort = asc ? SORT_ASC : SORT_DESC;
        PageRequest page = PageRequest.of(from / size, size, sort);
        List<Show> shows = repository.findShowsByPetId(petId, page).getContent();
        return returnFullDto(shows);
    }

    @Override
    public ShowFullDto update(Long userId, Long showId, ShowUpdateDto showUpdateDto) {
        Show show = getOrNotFound(showId);
        unionService.checkOwner(userId, show.getPetId());
        updateNotNullFields(show, showUpdateDto);
        repository.save(show);
        return returnFullDto(show);
    }

    private void updateNotNullFields(Show show, ShowUpdateDto dto) {
        if (dto.getShowRank() != null) {
            show.setShowRank(dto.getShowRank());
        }
        if (dto.getClub() != null) {
            show.setClub(dto.getClub());
        }
        if (dto.getAgeClass() != null) {
            show.setAgeClass(dto.getAgeClass());
        }
        if (dto.getMark() != null) {
            show.setMark(dto.getMark());
        }
        if (dto.getTitle() != null) {
            show.setTitle(dto.getTitle());
        }
        if (dto.getDate() != null) {
            show.setDate(dto.getDate());
        }
        if (dto.getPhotoUrl() != null) {
            show.setPhotoUrl(dto.getPhotoUrl());
        }
    }

    private Show getOrNotFound(long showId) {
        return repository.findById(showId)
                .orElseThrow(() -> new NotFoundException(Breed.class, String.format("Show participation with id %d not found", showId)));
    }
}
