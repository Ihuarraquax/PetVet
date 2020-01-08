package pl.zablocki.petvet.entity;

import pl.zablocki.petvet.model.Credentials;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Credentials credentials;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Pet> pets;

    public Owner() {
    }

    public Owner(User user, Credentials credentials) {
        this.user = user;
        this.credentials = credentials;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
