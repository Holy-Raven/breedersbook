package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.pet.repository.PetRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl {
    private final PetRepository repository;
}
