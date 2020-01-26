package pl.zablocki.petvet.controllers.commands;

import org.springframework.data.jpa.domain.Specification;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.Owner_;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.Pet_;

import javax.persistence.criteria.*;

public class PetSpecification implements Specification<Pet> {

    private SearchCriteria criteria;

    public PetSpecification(String phrase) {
        criteria = new SearchCriteria(phrase);

    }

    @Override
    public Predicate toPredicate
            (Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //Join<Pet, Owner> join = root.join(Pet_.owner);
        if (criteria.phrase != null) {
            return builder.or(
                    builder.or(
                            builder.like(builder.lower(root.get(Pet_.NAME)), criteria.phrase.toLowerCase()),
                            builder.like(builder.lower(root.get(Pet_.BREED)), criteria.phrase.toLowerCase())
                    ),
                    builder.or(
                            builder.like(builder.lower(root.get(Pet_.SEX)), criteria.phrase.toLowerCase()))
            );
        }
        return null;
    }
}