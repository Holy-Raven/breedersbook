package ru.codesquad.show.mapper;

import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowShortDto;
import ru.codesquad.show.model.Show;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ShowMapper {

    public static ShowFullDto returnFullDto(Show show) {
        return ShowFullDto.builder()
                .id(show.getId())
                .showRank(show.getShowRank())
                .club(show.getClub())
                .ageClass(show.getAgeClass())
                .mark(show.getMark())
                .title(show.getTitle())
                .photoUrl(show.getPhotoUrl())
                .build();
    }

    public static ShowShortDto returnShortDto(Show show) {
        return ShowShortDto.builder()
                .id(show.getId())
                .showRank(show.getShowRank())
                .mark(show.getMark())
                .title(show.getTitle())
                .date(show.getDate())
                .build();
    }

    public static Show returnShow(ShowNewDto dto, long petId) {
        return Show.builder()
                .petId(petId)
                .showRank(dto.getShowRank())
                .club(dto.getClub())
                .ageClass(dto.getAgeClass())
                .mark(dto.getMark())
                .title(dto.getTitle())
                .date(dto.getDate())
                .build();
    }

    public static List<ShowFullDto> returnFullDto(List<Show> shows) {

        return shows.isEmpty() ? Collections.emptyList()
                : shows.stream().map(ShowMapper::returnFullDto).collect(Collectors.toList());
    }
}
