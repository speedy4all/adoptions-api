package com.p5.adoptions.model;

public class Dog extends Animal implements AnimalInterface
{


    public Dog(String name, String photo)
    {
        super(name, photo);
    }

    @Override
    public String makeSound()
    {
        return "Ham";
    }

    @Override
    public String whatDoesItEats()
    {
        return "Bones";
    }

    @Override
    public String howManyLegs()
    {
        return "Four";
    }
}
