package pl.zablocki.petvet.entity.appointments;

import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.Vet;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private Pet pet;
    private String symptomsDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    private Vet vet;
    private boolean isApproved;

    public Appointment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", owner=" + owner +
                ", pet=" + pet +
                ", symptomsDescription='" + symptomsDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", vet=" + vet +
                ", isApproved=" + isApproved +
                '}';
    }
}
