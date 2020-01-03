package pl.zablocki.petvet.entity;

import pl.zablocki.petvet.model.Credentials;

import javax.persistence.*;
import java.util.List;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Credentials credentials;
    @OneToMany
    private List<Pet> pets;

}
