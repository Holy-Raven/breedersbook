package ru.codesquad.breed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.repository.BreedRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {
    private final BreedRepository repository;

}