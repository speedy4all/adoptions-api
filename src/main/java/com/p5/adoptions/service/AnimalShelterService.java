package com.p5.adoptions.service;

import com.p5.adoptions.repository.cats.Cat;
import com.p5.adoptions.repository.dogs.Dog;
import com.p5.adoptions.repository.shelters.AnimalShelter;
import com.p5.adoptions.repository.shelters.AnimalShelterRepository;
import com.p5.adoptions.service.DTO.CatDTO;
import com.p5.adoptions.service.DTO.ListDTO;
import com.p5.adoptions.service.DTO.ShelterDTO;
import com.p5.adoptions.service.adapters.CatAdapter;
import com.p5.adoptions.service.adapters.ShelterAdapter;
import com.p5.adoptions.service.exceptions.ApiError;
import com.p5.adoptions.service.exceptions.ShelterLocationException;
import com.p5.adoptions.service.exceptions.ValidationException;
import com.p5.adoptions.service.exceptions.Violation;
import com.p5.adoptions.service.validations.OnCreate;
import com.p5.adoptions.service.validations.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class AnimalShelterService
{
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalShelterService(AnimalShelterRepository animalShelterRepository)
    {
        this.animalShelterRepository = animalShelterRepository;
    }

    public ListDTO<ShelterDTO> findAll()
    {
        ListDTO<ShelterDTO> listDTO = new ListDTO<>();

        List<AnimalShelter> allShelters = animalShelterRepository.findAll();

        listDTO.setData(ShelterAdapter.toDTOList(allShelters));
        listDTO.setTotalCount(animalShelterRepository.count());

        return listDTO;
    }

    @Validated(OnCreate.class)
    public ShelterDTO createShelter(@Valid ShelterDTO animalShelter)
    {
        validateShelterLocation(animalShelter);

        AnimalShelter shelter = ShelterAdapter.fromDTO(animalShelter);
        return ShelterAdapter.toDTO(animalShelterRepository.save(shelter));
    }

    // public for Test visibility
    public void validateShelterLocation(ShelterDTO animalShelter)
    {
        String location = animalShelter.getLocation().toLowerCase(Locale.ROOT);
        if (!location.contains("brasov") && !location.contains("iasi"))
        {
            throw new ShelterLocationException("Brasov or Iasi is required");
        }
    }

    private void validateShelter(ShelterDTO shelterDTO)
    {
        ApiError error = new ApiError(HttpStatus.CONFLICT, "Shelter validation failed");

        if (shelterDTO.getDogs().isEmpty())
        {
            error.getViolations().add(new Violation("dogs", "Minimum 1 dog pls"));
        }
        if (shelterDTO.getName().contains("_"))
        {
            error.getViolations().add(new Violation("name", "No underscore('_') in name "));
        }

        if (!error.getViolations().isEmpty())
        {
            throw new ValidationException(error);
        }
    }

    @Validated(OnUpdate.class)
    public ShelterDTO updateShelter(Integer id, @Valid ShelterDTO animalShelter)
    {
        validateShelterLocation(animalShelter);
        validateShelter(animalShelter);


        AnimalShelter shelter = getShelterById(id);
        if (!shelter.getId().equals(animalShelter.getId()))
        {
            throw new RuntimeException("Id of entity not the same with path id");
        }

        return ShelterAdapter.toDTO(animalShelterRepository.save(ShelterAdapter.fromDTO(animalShelter)));
    }

    public ShelterDTO findById(Integer id)
    {
        return ShelterAdapter.toDTO(getShelterById(id));
    }

    public void deleteShelter(Integer id)
    {
        animalShelterRepository.deleteById(id);
    }

    public List<CatDTO> findAllCatsByShelter(Integer shelterId)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        return CatAdapter.toDTOList(shelter.getCats());
    }

    public List<CatDTO> addNewCatToShelter(Integer shelterId, CatDTO cat)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getCats().add(CatAdapter.fromDTO(cat));

        animalShelterRepository.save(shelter);

        return CatAdapter.toDTOList(shelter.getCats());
    }

    public Cat updateCatInShelter(Integer shelterId, Integer catId, Cat cat)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Cat> newCats = shelter.getCats().stream().map(c -> {
            if (c.getId().equals(catId))
            {
                cat.setId(catId);
                return cat;
            }
            return c;
        }).collect(Collectors.toList());

        shelter.setCats(newCats);

        animalShelterRepository.save(shelter);

        return cat;
    }

    public void deleteCatInShelter(Integer shelterId, Integer catId)
    {
        AnimalShelter shelter = getShelterById(shelterId);
        List<Cat> newCats = shelter.getCats()
                                   .stream()
                                   .filter(c -> !c.getId().equals(catId))
                                   .collect(Collectors.toList());
        shelter.setCats(newCats);
        animalShelterRepository.save(shelter);
    }

    public List<Dog> findAllDogsByShelter(Integer shelterId)
    {
        AnimalShelter shelter = getShelterById(shelterId);
        return shelter.getDogs();
    }

    private AnimalShelter getShelterById(Integer id)
    {
        Optional<AnimalShelter> optional = animalShelterRepository.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException("Shelter with id " + id + " not found"));
    }

    public List<Dog> addNewDogToShelter(Integer shelterId, Dog dog)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getDogs().add(dog);

        animalShelterRepository.save(shelter);

        return shelter.getDogs();
    }

    public Dog updateDogInShelter(Integer shelterId, Integer dogId, Dog dog)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Dog> newDogs = shelter.getDogs().stream().map(d -> {
            if (d.getId().equals(dogId))
            {
                dog.setId(dogId);
                return dog;
            }
            return d;
        }).collect(Collectors.toList());

        shelter.setDogs(newDogs);

        animalShelterRepository.save(shelter);

        return dog;
    }

    public void deleteDogInShelter(Integer shelterId, Integer dogId)
    {
        AnimalShelter shelter = getShelterById(shelterId);

        boolean removed = shelter.getDogs().removeIf(d -> d.getId().equals(dogId));

        animalShelterRepository.save(shelter);

        if (!removed)
        {
            throw new RuntimeException("Already deleted or entity missing");
        }
    }
}
