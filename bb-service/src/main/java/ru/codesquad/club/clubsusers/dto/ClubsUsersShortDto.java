package ru.codesquad.club.clubsusers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Short Clubs Users")
public class ClubsUsersShortDto {

    Long clubId;
    Long userId;
    String role;
}
