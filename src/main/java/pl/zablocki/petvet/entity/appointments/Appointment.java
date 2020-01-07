package pl.zablocki.petvet.entity.appointments;

import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.Pet;

public class Appointment {

    private Owner owner;
    private Pet pet;
    private String symptomsDescription;

    public Appointment() {
    }

    public Appointment(Owner owner, Pet pet, String symptomsDescription) {
        this.owner = owner;
        this.pet = pet;
        this.symptomsDescription = symptomsDescription;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getSymptomsDescription() {
        return symptomsDescription;
    }

    public void setSymptomsDescription(String symptomsDescription) {
        this.symptomsDescription = symptomsDescription;
    }
}
