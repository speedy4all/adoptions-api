package com.p5.adoptions.service;

import com.p5.adoptions.repository.animal.AnimalRepository;
import com.p5.adoptions.repository.cats.Cat;
import com.p5.adoptions.repository.cats.CatRepository;
import com.p5.adoptions.repository.shelters.AnimalShelter;
import com.p5.adoptions.repository.shelters.AnimalShelterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalShelterService
{
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalShelterService(AnimalShelterRepository animalShelterRepository)
    {
        this.animalShelterRepository = animalShelterRepository;
    }

    public List<AnimalShelter> getAll() {
        return animalShelterRepository.findAll();
    }

    public AnimalShelter createShelter(AnimalShelter shelter) {
        return animalShelterRepository.save(shelter);
    }

    public AnimalShelter updateShelter(Integer id, AnimalShelter shelter) {
        Optional<AnimalShelter> oldShelter = animalShelterRepository.findById(id);

        if(oldShelter.isPresent()) {
            AnimalShelter shelterObj = oldShelter.get();
            shelterObj.setName(shelter.getName());
            shelterObj.setCats(shelter.getCats());
            shelterObj.setDogs(shelter.getDogs());
            return animalShelterRepository.save(shelterObj);

        } else {
            throw new EntityNotFoundException("Shelter" + id + " not found");
        }

    }

    public List<Cat> getAllCatsForShelter(Integer id) {
        Optional<AnimalShelter> shelter = animalShelterRepository.findById(id);
        if(shelter.isPresent()) {
            return shelter.get().getCats();
        } else {
            throw new EntityNotFoundException("Shelter" + id + " not found");
        }
    }

    public List<Cat> createNewCat(Integer id, Cat cat) {
        Optional<AnimalShelter> shelter = animalShelterRepository.findById(id);
        if(shelter.isPresent()) {
            AnimalShelter newShelter = shelter.get();
            List<Cat> cats = newShelter.getCats();
            cats.add(cat);
            AnimalShelter result = animalShelterRepository.save(newShelter);
            return result.getCats();
        } else {
            throw new EntityNotFoundException("Shelter" + id + " not found");
        }
    }

    public void deleteShelter(Integer id) {
        animalShelterRepository.deleteById(id);
    }
}
