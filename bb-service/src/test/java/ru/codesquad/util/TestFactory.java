package ru.codesquad.util;

import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.location.Location;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.codesquad.show.mapper.ShowMapper.returnShow;

public class TestFactory {

    public static Show makeFilledShow(int number, long petId) {
        Show show = returnShow(makeNewShowDto(number), petId);
        show.setId((long) number);
        return show;
    }

    public static ShowUpdateDto makeUpdateDto(int number) {
        return ShowUpdateDto.builder()
                .mark("mark" + number)
                .title("title" + number)
                .build();
    }

    public static ShowNewDto makeNewShowDto(int number) {
        return ShowNewDto.builder()
                .showRank("rank" + number)
                .club("club" + number)
                .ageClass("age class" + number)
                .mark("mark" + number)
                .title("title" + number)
                .date(LocalDate.now().minusDays(number))
                .photoUrl("https://photo-url" + number + ".com")
                .build();
    }

    public static User makeFilledUser(int number) {
        UserInfo userInfo = makeNewUserInfo(number);
        userInfo.setId((long) number);
        Kennel kennel = makeNewKennel(number);
        kennel.setId((long) number);
        User user = makeNewUser(userInfo, kennel, number);
        user.setId((long) number);
        return user;
    }

    public static Pet makeFilledCat(int number) {
        User owner = makeFilledUser(number);
        Breed breed = makeNewBreed();
        breed.setId((long) number);
        Kennel kennel = owner.getKennel();
        Pet cat = makeNewCat(breed, owner, kennel, number);
        cat.setId((long) number);
        return cat;
    }

    public static User makeNewUser(UserInfo userInfo, Kennel kennel, int number) {
        return User.builder()
                .firstName("Name" + number)
                .lastName("LastName" + number)
                .username("Login" + number)
                .password("Password" + number)
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
                .gender(Gender.FEMALE)
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
