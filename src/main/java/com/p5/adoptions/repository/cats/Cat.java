package com.p5.adoptions.repository.cats;

import com.p5.adoptions.repository.animal.Animal;

import javax.persistence.Entity;

@Entity
public class Cat extends Animal
{

    //    Bidirectional
    //    @ManyToOne
    //    @JoinColumn(name = "shelter_id")
    //    private AnimalShelter shelter;

}
