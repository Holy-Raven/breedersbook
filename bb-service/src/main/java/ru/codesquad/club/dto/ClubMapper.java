package ru.codesquad.club.dto;

import ru.codesquad.breed.Breed;
import ru.codesquad.breed.mapper.BreedMapper;
import ru.codesquad.club.Club;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

public class ClubMapper {

    public static Club returnClub(ClubNewDto clubNewDto, Breed breed) {
        PetType type = EnumUtil.getValue(PetType.class, clubNewDto.getPetType());
        return Club.builder()
                .type(type)
                .breed(breed)
                .name(clubNewDto.getName())
                .descriptions(clubNewDto.getDescriptions())
                .photo(clubNewDto.getPhoto())
                .created(clubNewDto.getCreated())
                .build();
    }

    public static ClubDto returnClubDto(Club club) {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .petType(club.getType())
                .name(club.getName())
                .descriptions(club.getDescriptions())
                .created(club.getCreated())
                .photo(club.getPhoto())
                .build();

        if (club.getBreed() != null) {
            clubDto.setBreed(BreedMapper.returnShortDto(club.getBreed()));
        }
        return clubDto;
    }

    public static ClubShortDto returnClubShortDto(Club club) {
        ClubShortDto clubShortDto = ClubShortDto.builder()
                .petType(club.getType())
                .name(club.getName())
                .descriptions(club.getDescriptions())
                .photo(club.getPhoto())
                .build();

        if (club.getBreed() != null) {
            clubShortDto.setBreed(BreedMapper.returnShortDto(club.getBreed()));
        }
        return clubShortDto;
    }
}