package ru.codesquad.mapper;

import lombok.experimental.UtilityClass;
import ru.codesquad.DateTimeConstant;
import ru.codesquad.dto.HitDto;
import ru.codesquad.model.PetHit;

import java.time.LocalDateTime;

@UtilityClass
public class PetHitMapper {
    public PetHit toHit(HitDto hitDto) {
        return PetHit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), DateTimeConstant.dtFormatter))
                .build();
    }
}
