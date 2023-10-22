package ru.codesquad.showPart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.dto.ShowPartNewDto;
import ru.codesquad.showPart.dto.ShowPartUpdateDto;
import ru.codesquad.showPart.model.ShowPart;
import ru.codesquad.showPart.repository.ShowPartRepository;
import ru.codesquad.util.UnionService;

import java.util.List;

import static ru.codesquad.showPart.mapper.ShowPartMapper.returnFullDto;
import static ru.codesquad.showPart.mapper.ShowPartMapper.returnShow;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowPartServiceImpl implements ShowPartService {
    private static final Sort SORT_DESC = Sort.by(Sort.Direction.DESC, "date");
    private static final Sort SORT_ASC = Sort.by(Sort.Direction.ASC, "date");

    private final ShowPartRepository repository;
    private final UnionService unionService;

    @Override
    public ShowPartFullDto getById(long showId) {
        ShowPart showPart = getOrNotFound(showId);
        return returnFullDto(showPart);
    }

    @Override
    @Transactional
    public ShowPartFullDto add(Long userId, Long petId, ShowPartNewDto showPartNewDto) {
        Pet pet = unionService.getPetOrNotFound(petId);
        unionService.checkOwner(userId, petId);
        ShowPart showPart = returnShow(showPartNewDto, pet);
        showPart = repository.save(showPart);
        log.info("Show participation with id {} was added to DB", showPart.getId());
        return returnFullDto(showPart);
    }

    @Override
    @Transactional
    public boolean delete(long showId) {
        log.info("Show with id {} was deleted by admin", showId);
        repository.deleteById(showId);
        return true;
    }

    @Override
    public ShowPartFullDto getByUserById(Long userId, long showId) {
        ShowPart showPart = getOrNotFound(showId);
        unionService.checkOwner(userId, showPart.getPetId());
        return returnFullDto(showPart);
    }

    @Override
    @Transactional
    public boolean deleteByUser(Long userId, long showId) {
        ShowPart showPart = getOrNotFound(showId);
        unionService.checkOwner(userId, showPart.getPetId());
        log.info("Show with id {} was deleted by user with id {}", showId, userId);
        repository.deleteById(showId);
        return true;
    }

    @Override
    public List<ShowPartFullDto> getByPetId(Long userId, long petId, Boolean asc, Integer from, Integer size) {
        unionService.checkOwner(userId, petId);
        Sort sort = asc ? SORT_ASC : SORT_DESC;
        PageRequest page = PageRequest.of(from / size, size, sort);
        List<ShowPart> showParts = repository.findShowsByPetId(petId, page).getContent();
        return returnFullDto(showParts);
    }

    @Override
    @Transactional
    public ShowPartFullDto update(Long userId, Long showId, ShowPartUpdateDto showPartUpdateDto) {
        ShowPart showPart = getOrNotFound(showId);
        unionService.checkOwner(userId, showPart.getPetId());
        updateNotNullFields(showPart, showPartUpdateDto);
        repository.save(showPart);
        return returnFullDto(showPart);
    }

    private void updateNotNullFields(ShowPart showPart, ShowPartUpdateDto dto) {
        if (dto.getShowRank() != null) {
            showPart.setShowRank(dto.getShowRank());
        }
        if (dto.getClub() != null) {
            showPart.setClub(dto.getClub());
        }
        if (dto.getAgeClass() != null) {
            showPart.setAgeClass(dto.getAgeClass());
        }
        if (dto.getMark() != null) {
            showPart.setMark(dto.getMark());
        }
        if (dto.getTitle() != null) {
            showPart.setTitle(dto.getTitle());
        }
        if (dto.getDate() != null) {
            showPart.setDate(dto.getDate());
        }
        if (dto.getPhotoUrl() != null) {
            showPart.setPhotoUrl(dto.getPhotoUrl());
        }
    }

    private ShowPart getOrNotFound(long showId) {
        return repository.findById(showId)
                .orElseThrow(() -> new NotFoundException(Breed.class, String.format("Show participation with id %d not found", showId)));
    }
}
