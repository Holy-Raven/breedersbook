package ru.codesquad.pet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.codesquad.breed.Breed;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(uses = {User.class, KennelMapper.class},
        componentModel = "spring",
        imports = {LocalDate.class, UUID.class, Gender.class, Breed.class},
        disableSubMappingMethodsGeneration = true)
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(target = "type", expression = "java(EnumUtil.getValue(PetType.class, PetNewDto.getPetType()))")
    @Mapping(target = "gender", expression = "java(EnumUtil.getValue(Gender.class, PetNewDto.getGender()))")
    @Mapping(target = "saleStatus", expression = "java(EnumUtil.getValue(SaleStatus.class, PetNewDto.getSaleStatus()))")
    @Mapping(target = "name", source = "newPetDto.name")
    Pet toPet(NewPetDto newPetDto, User owner);

    //@NotBlank
    //    @Size(min = 3, max = 3)
    //    String petType;
    //    @NotBlank
    //    @Size(min = 4, max = 6)
    //    String gender;
    //    @NotBlank
    //    @Size(min = 1, max = 24)
    //    String pattern;
    //    @NotNull
    //    Set<String> colors = new HashSet<>();
    //    @NotBlank
    //    @Size(min = 1, max = 2000)
    //    String temper;
    //    @NotBlank
    //    @Size(min = 1, max = 5000)
    //    String description;
    //    @NotBlank
    //    @Size(min = 1, max = 250)
    //    String name;
    //    @PastOrPresent
    //    LocalDate birthDate;
    //    @PositiveOrZero
    //    int price;
    //    boolean forSale;
    //    String passportImg;
    //    boolean sterilization;
    //    @NotNull
    //    Long breedId;

    PetFullDto toFullDto(Pet pet);

    PetShortDto toShortDto(Pet pet);
}