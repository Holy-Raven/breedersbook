package ru.codesquad.mapper;

import lombok.experimental.UtilityClass;
import ru.codesquad.dto.HitDto;
import ru.codesquad.model.KennelHit;
import ru.codesquad.util.Constant;

import java.time.LocalDateTime;

@UtilityClass
public class KennelHitMapper {
    public KennelHit toHit(HitDto hitDto) {
        return KennelHit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), Constant.FORMATTER))
                .build();
    }
}