package ru.codesquad.club.clubsusers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Full Clubs Users")
public class ClubsUsersFullDto {

    Long clubId;
    Long userId;
    String role;
    String status;
    LocalDate created;

}
