package com.p5.adoptions.service;

import com.p5.adoptions.repository.animal.Animal;
import com.p5.adoptions.repository.animal.AnimalRepository;
import com.p5.adoptions.repository.shelters.AnimalShelterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalShelterService
{
    private final AnimalShelterRepository animalShelterRepository;
    private final AnimalRepository animalRepository;

    public AnimalShelterService(AnimalShelterRepository animalShelterRepository, AnimalRepository animalRepository)
    {
        this.animalShelterRepository = animalShelterRepository;
        this.animalRepository = animalRepository;
    }

    //....
}
