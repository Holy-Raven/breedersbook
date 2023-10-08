package ru.codesquad.pet.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.mapper.BreedMapper;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.enums.CatPattern;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.DogPattern;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;
import ru.codesquad.user.dto.UserMapper;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PetMapperImpl implements PetMapper {
    private final BreedMapper breedMapper;
    private final KennelMapper kennelMapper;
    private final UserMapper userMapper;

    @Override
    public Pet returnPet(PetNewDto petNewDto, User owner, Breed breed) {
        PetType type = EnumUtil.getValue(PetType.class, petNewDto.getPetType());
        if (type == PetType.CAT) {
            EnumUtil.getValue(CatPattern.class, petNewDto.getPattern());
        } else {
            EnumUtil.getValue(DogPattern.class, petNewDto.getPattern());
        }
        List<Color> colors = petNewDto.getColors().stream()
                .map(colorString -> EnumUtil.getValue(Color.class, colorString))
                .collect(Collectors.toList());
        SaleStatus saleStatus = petNewDto.isForSale() ? SaleStatus.FOR_SALE : SaleStatus.NOT_FOR_SALE;
        return Pet.builder()
                .type(type)
                .gender(EnumUtil.getValue(Gender.class, petNewDto.getGender()))
                .pattern(petNewDto.getPattern())
                .colors(colors)
                .owner(owner)
                .breed(breed)
                .birthDate(petNewDto.getBirthDate())
                .kennel(owner.getKennel())
                .price(petNewDto.getPrice())
                .name(petNewDto.getName())
                .description(petNewDto.getDescription())
                .temper(petNewDto.getTemper())
                .saleStatus(saleStatus)
                .passportImg(petNewDto.getPassportImg())
                .sterilized(petNewDto.isSterilized())
                .build();
    }

    @Override
    public PetFullDto returnFullDto(Pet pet) {
        return PetFullDto.builder()
                .id(pet.getId())
                .petType(pet.getType())
                .pattern(pet.getPattern())
                .colors(pet.getColors())
                .sterilized(pet.isSterilized())
                .birthDate(pet.getBirthDate())
                .breed(breedMapper.returnShortDto(pet.getBreed()))
                .name(pet.getName())
                .description(pet.getDescription())
                .gender(pet.getGender())
                .temper(pet.getTemper())
                .price(pet.getPrice())
                .kennel(kennelMapper.returnKennelDto(pet.getKennel()))
                .owner(userMapper.returnUserShortDto(pet.getOwner()))
                .passportImg(pet.getPassportImg())
                .saleStatus(pet.getSaleStatus())
                .build();
    }

    @Override
    public PetShortDto returnShortDto(Pet pet) {
        return PetShortDto.builder()
                .id(pet.getId())
                .gender(pet.getGender())
                .petType(pet.getType())
                .pattern(pet.getPattern())
                .colors(pet.getColors())
                .name(pet.getName())
                .description(pet.getDescription())
                .birthDate(pet.getBirthDate())
                .price(pet.getPrice())
                .breed(breedMapper.returnShortDto(pet.getBreed()))
                .kennel(kennelMapper.returnKennelDto(pet.getKennel()))
                .build();
    }

    @Override
    public List<PetShortDto> returnShortDtoList(List<Pet> pets) {
        if (pets.isEmpty()) {
            return Collections.emptyList();
        }
        return pets.stream().map(this::returnShortDto).collect(Collectors.toList());
    }

    @Override
    public List<PetFullDto> returnFullDtoList(List<Pet> pets) {
        if (pets.isEmpty()) {
            return Collections.emptyList();
        }
        return pets.stream().map(this::returnFullDto).collect(Collectors.toList());
    }

}