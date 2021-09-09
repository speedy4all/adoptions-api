package com.p5.adoptions.service.DTO;

import com.p5.adoptions.service.validations.OnCreate;
import com.p5.adoptions.service.validations.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public class ShelterDTO {

    @NotNull(message = "Id must not be null for update", groups = OnUpdate.class)
    @Null(message = "Id must be empty or null for creation", groups = OnCreate.class)
    @Min(value = 1, groups = OnUpdate.class)
    private Integer id;

    @NotBlank
    @NotNull
    private String name;
    private String location;
    private List<CatDTO> cats = new ArrayList<>();
    private List<DogDTO> dogs = new ArrayList<>();

    public ShelterDTO() {
    }

    public ShelterDTO(Integer id, String name, String location, List<CatDTO> cats, List<DogDTO> dogs) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cats = cats;
        this.dogs = dogs;
    }

    public Integer getId() {
        return id;
    }

    public ShelterDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShelterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ShelterDTO setLocation(String location) {
        this.location = location;
        return this;
    }

    public List<CatDTO> getCats() {
        return cats;
    }

    public ShelterDTO setCats(List<CatDTO> cats) {
        this.cats = cats;
        return this;
    }

    public List<DogDTO> getDogs() {
        return dogs;
    }

    public ShelterDTO setDogs(List<DogDTO> dogs) {
        this.dogs = dogs;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ShelterDTO that = (ShelterDTO) o;

        if (!id.equals(that.id))
        {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
