package ru.codesquad.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Stats {
    private String app;
    private String uri;
    private long hits;
}
