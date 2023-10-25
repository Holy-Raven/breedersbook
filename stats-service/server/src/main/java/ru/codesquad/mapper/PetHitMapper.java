package ru.codesquad.mapper;

import ru.codesquad.dto.HitDto;
import ru.codesquad.model.PetHit;
import ru.codesquad.util.Constant;

import java.time.LocalDateTime;

public class PetHitMapper {
    public static PetHit toHit(HitDto hitDto) {
        return PetHit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), Constant.FORMATTER))
                .build();
    }
}
