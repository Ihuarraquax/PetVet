package pl.zablocki.petvet.entity;

import pl.zablocki.petvet.model.Credentials;
import pl.zablocki.petvet.model.Speciality;

import javax.persistence.*;

@Entity
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Credentials credentials;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    public Vet() {
    }

    public Vet(User user, Credentials credentials, Speciality speciality) {
        this.user = user;
        this.credentials = credentials;
        this.speciality = speciality;
    }


    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
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

    @Override
    public String toString() {
        return credentials.getFirstName() +" "+ credentials.getLastName() +" - ("+speciality+")";
    }
}
