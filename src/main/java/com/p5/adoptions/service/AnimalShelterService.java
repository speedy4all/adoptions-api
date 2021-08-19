package com.p5.adoptions.service;

import com.p5.adoptions.repository.animal.Animal;
import com.p5.adoptions.repository.animal.AnimalRepository;
import com.p5.adoptions.repository.cats.Cat;
import com.p5.adoptions.repository.dogs.Dog;
import com.p5.adoptions.repository.shelters.AnimalShelter;
import com.p5.adoptions.repository.shelters.AnimalShelterRepository;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalShelterService {
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalShelterService(AnimalShelterRepository animalShelterRepository) {
        this.animalShelterRepository = animalShelterRepository;
    }

    public List<AnimalShelter> findAll() {
        return animalShelterRepository.findAll();
    }

    public AnimalShelter createShelter(AnimalShelter animalShelter) {
        return animalShelterRepository.save(animalShelter);
    }

    public AnimalShelter updateShelter(Integer id, AnimalShelter animalShelter) {
        AnimalShelter shelter = getShelterById(id);
        if (!shelter.getId().equals(animalShelter.getId())) {
            throw new RuntimeException("Id of entity not the same with path id");
        }

        return animalShelterRepository.save(animalShelter);
    }

    public AnimalShelter findById(Integer id) {
        return getShelterById(id);
    }

    public void deleteShelter(Integer id) {
        animalShelterRepository.deleteById(id);
    }

    public List<Cat> findAllCatsByShelter(Integer shelterId) {
        AnimalShelter shelter = getShelterById(shelterId);

        return shelter.getCats();
    }

    public List<Cat> addNewCatToShelter(Integer shelterId, Cat cat) {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getCats().add(cat);

        animalShelterRepository.save(shelter);

        return shelter.getCats();
    }

    public Cat updateCatInShelter(Integer shelterId, Integer catId, Cat cat) {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Cat> newCats = shelter.getCats().stream().map(c -> {
            if (c.getId().equals(catId)) {
                cat.setId(catId);
                return cat;
            }
            return c;
        }).collect(Collectors.toList());

        shelter.setCats(newCats);

        animalShelterRepository.save(shelter);

        return cat;
    }

    public void deleteCatInShelter(Integer shelterId, Integer catId) {
        AnimalShelter shelter = getShelterById(shelterId);
        List<Cat> newCats = shelter.getCats().stream().filter(c -> !c.getId().equals(catId)).collect(Collectors.toList());
        shelter.setCats(newCats);
        animalShelterRepository.save(shelter);
    }

    public List<Dog> findAllDogsByShelter(Integer shelterId) {
        AnimalShelter shelter = getShelterById(shelterId);
        return shelter.getDogs();
    }

    private AnimalShelter getShelterById(Integer id) {
        Optional<AnimalShelter> optional = animalShelterRepository.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException("Shelter with id " + id + " not found"));
    }

    public List<Dog> addNewDogToShelter(Integer shelterId, Dog dog) {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getDogs().add(dog);

        animalShelterRepository.save(shelter);

        return shelter.getDogs();
    }

    public Dog updateDogInShelter(Integer shelterId, Integer dogId, Dog dog) {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Dog> newDogs = shelter.getDogs().stream().map(d -> {
            if (d.getId().equals(dogId)) {
                dog.setId(dogId);
                return dog;
            }
            return d;
        }).collect(Collectors.toList());

        shelter.setDogs(newDogs);

        animalShelterRepository.save(shelter);

        return dog;
    }

    public void deleteDogInShelter(Integer shelterId, Integer dogId) {
        AnimalShelter shelter = getShelterById(shelterId);

        boolean removed = shelter.getDogs().removeIf(d -> d.getId().equals(dogId));

        animalShelterRepository.save(shelter);

        if(!removed) {
            throw new RuntimeException("Already deleted or entity missing");
        }
    }
}
