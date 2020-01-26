package pl.zablocki.petvet.controllers.commands;

import org.springframework.data.jpa.domain.Specification;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.Pet_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PetSpecification implements Specification<Pet> {

    private SearchCriteria criteria;

    public PetSpecification(String phrase) {
        criteria = new SearchCriteria(phrase);

    }

    @Override
    public Predicate toPredicate
            (Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.like(root.get(Pet_.NAME), "%"+criteria.phrase+"%");

    }
}