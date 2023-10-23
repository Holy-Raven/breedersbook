package ru.codesquad.mapper;

import lombok.experimental.UtilityClass;
import ru.codesquad.dto.HitDto;
import ru.codesquad.model.PetHit;
import ru.codesquad.util.Constant;

import java.time.LocalDateTime;

@UtilityClass
public class PetHitMapper {
    public PetHit toHit(HitDto hitDto) {
        return PetHit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), Constant.FORMATTER))
                .build();
    }
}
