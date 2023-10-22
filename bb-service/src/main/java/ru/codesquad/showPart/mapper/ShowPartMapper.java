package ru.codesquad.showPart.mapper;

import ru.codesquad.pet.model.Pet;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.dto.ShowPartNewDto;
import ru.codesquad.showPart.dto.ShowPartShortDto;
import ru.codesquad.showPart.enums.ClassCats;
import ru.codesquad.showPart.enums.ClassDogs;
import ru.codesquad.showPart.enums.Marks;
import ru.codesquad.showPart.model.ShowPart;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ShowPartMapper {

    public static ShowPartFullDto returnFullDto(ShowPart showPart) {
        return ShowPartFullDto.builder()
                .id(showPart.getId())
                .petId(showPart.getPetId())
                .showRank(showPart.getShowRank())
                .club(showPart.getClub())
                .ageClass(showPart.getAgeClass())
                .mark(showPart.getMark())
                .title(showPart.getTitle())
                .date(showPart.getDate())
                .photoUrl(showPart.getPhotoUrl())
                .build();
    }

    public static ShowPartShortDto returnShortDto(ShowPart showPart) {
        return ShowPartShortDto.builder()
                .id(showPart.getId())
                .petId(showPart.getPetId())
                .showRank(showPart.getShowRank())
                .mark(showPart.getMark())
                .title(showPart.getTitle())
                .date(showPart.getDate())
                .build();
    }

    public static ShowPart returnShow(ShowPartNewDto dto, Pet pet) {
        if (pet.getType() == PetType.CAT) {
            EnumUtil.getValue(ClassCats.class, dto.getAgeClass());
        } else {
            EnumUtil.getValue(ClassDogs.class, dto.getAgeClass());
        }
        EnumUtil.getValue(Marks.class, dto.getMark());
        return ShowPart.builder()
                .petId(pet.getId())
                .showRank(dto.getShowRank())
                .club(dto.getClub())
                .ageClass(dto.getAgeClass())
                .mark(dto.getMark())
                .title(dto.getTitle())
                .date(dto.getDate())
                .photoUrl(dto.getPhotoUrl())
                .build();
    }

    public static List<ShowPartFullDto> returnFullDto(List<ShowPart> showParts) {
        return showParts.isEmpty() ? Collections.emptyList()
                : showParts.stream().map(ShowPartMapper::returnFullDto).collect(Collectors.toList());
    }
}
