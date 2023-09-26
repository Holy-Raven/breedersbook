package ru.codesquad.pet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.Breed;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.util.enums.PetType;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.user.User;
import ru.codesquad.util.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pets", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    PetType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    Gender gender;

    @Column(name = "pattern", nullable = false)
    String pattern;

    @ElementCollection
    @CollectionTable(name = "pets_color", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    Set<Color> colors = new HashSet<>();

    @Column(name = "temper", nullable = false)
    String temper;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate birthDate;

    @Column(name = "death_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate deathDate;

    @Column(name = "price")
    int price;

    @Column(name = "sale_status")
    SaleStatus saleStatus;

    @Column(name = "sale_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate saleDate;

    @Column(name = "passport_img")
    String passportImg;

    @Column(name = "sterilization")
    boolean sterilization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kennel_id")
    Kennel kennel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    Breed breed;
}