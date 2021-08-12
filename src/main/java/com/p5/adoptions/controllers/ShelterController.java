package com.p5.adoptions.controllers;


import com.p5.adoptions.repository.cats.Cat;
import com.p5.adoptions.repository.shelters.AnimalShelter;
import com.p5.adoptions.service.AnimalShelterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shelters")
public class ShelterController {

    private AnimalShelterService animalShelterService;

    public ShelterController(AnimalShelterService animalShelterService) {
        this.animalShelterService = animalShelterService;
    }

    @GetMapping()
    public ResponseEntity<List<AnimalShelter>> getShelters() {
        return ResponseEntity.ok(animalShelterService.getAll());
    }

    @PostMapping()
    public ResponseEntity<AnimalShelter> createShelter(@RequestBody AnimalShelter shelter) {
        return ResponseEntity.ok(animalShelterService.createShelter(shelter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalShelter> updateShelter(@PathVariable("id") Integer id, @RequestBody AnimalShelter shelter) {
        return ResponseEntity.ok(animalShelterService.updateShelter(id, shelter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> updateShelter(@PathVariable("id") Integer id) {
        animalShelterService.deleteShelter(id);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @GetMapping("/{id}/cats")
    public ResponseEntity<List<Cat>> getAllCatsForShelter(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(animalShelterService.getAllCatsForShelter(id));
    }

    @PostMapping("/{id}/cats")
    public ResponseEntity<List<Cat>> addNewCatToShelter(@PathVariable("id") Integer id, @RequestBody Cat cat) {
        return ResponseEntity.ok(animalShelterService.createNewCat(id, cat));
    }
}
