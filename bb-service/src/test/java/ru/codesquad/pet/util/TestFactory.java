package ru.codesquad.pet.util;

import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.location.Location;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestFactory {

    public static User makeNewUser(UserInfo userInfo, Kennel kennel, int number) {
        return User.builder()
                .firstName("Name" + number)
                .userInfo(userInfo)
                .kennel(kennel)
                .created(LocalDateTime.now())
                .email("e" + number + "@mail.com")
                .username("login" + number)
                .build();
    }

    public static Breed makeNewBreed() {
        return Breed.builder()
                .petType(PetType.CAT)
                .name("NoName")
                .description("Description")
                .furType(FurType.SHORT)
                .photoUrl("https://photo_url.com")
                .build();
        //(description, fur_type, name, pet_type, photo_url)
    }

    public static Pet makeNewCat(Breed breed, User owner, Kennel kennel, int number) {
        return Pet.builder()
                .type(PetType.CAT)
                .breed(breed)
                .owner(owner)
                .gender(Gender.FEMALE)
                .pattern("solid")
                .colors(List.of(Color.BLACK))
                .birthDate(LocalDate.now().minusMonths(number))
                .name("Name" + number)
                .description("Description" + number)
                .saleStatus(SaleStatus.NOT_FOR_SALE)
                .passportImg("https://passport-url" + number + ".com")
                .temper("Angry small evil")
                .kennel(kennel)
                .build();
    }

    public static UserInfo makeNewUserInfo(int number) {
        return UserInfo.builder()
                .birthDate(LocalDateTime.now().minusYears(31))
                .description("Description" + number)
                .phone("+7900123456" + number)
                .photo("https://photo-url" + number + ".com")
                .build();
    }

    public static Kennel makeNewKennel(int number) {
        return Kennel.builder()
                .name("Name" + number)
                .descriptions("Description" + number)
                .phone("+7900123456" + number)
                .created(LocalDateTime.now())
                .build();
    }

    public static Location makeNewLocation(Integer number) {
        return Location.builder()
                .country("Russia" + number)
                .city("Moscow" + number)
                .street("Arbat" + number)
                .house(String.valueOf(number))
                .apartment(number)
                .build();
    }
}
