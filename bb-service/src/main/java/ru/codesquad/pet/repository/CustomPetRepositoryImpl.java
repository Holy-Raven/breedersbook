package ru.codesquad.pet.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.pet.model.PublicSearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomPetRepositoryImpl implements CustomPetRepository {
    private final EntityManager entityManager;

    @Override
    public List<Pet> getAllByCriteria(PrivateSearchCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = builder.createQuery(Pet.class);
        Root<Pet> root = criteriaQuery.from(Pet.class);

        List<Predicate> predicates = new ArrayList<>();

        //<!-- Filling list of predicates
        Predicate ownerId = builder.equal(root.get("owner_id"), criteria.getUserId());
        predicates.add(ownerId);

        if (criteria.getGender() != null) {
            Predicate genderPr = builder.equal(root.get("gender"), criteria.getGender());
            predicates.add(genderPr);
        }

        if (criteria.getSaleStatus() != null) {
            Predicate saleStatus = builder.equal(root.get("sale_status"), criteria.getSaleStatus());
            predicates.add(saleStatus);
        }
        //-->
        Order order = getOrderBySort(criteria.getSort(), builder, root);

        return getPets(criteriaQuery, root, predicates, order, criteria.getFrom(), criteria.getSize());
    }

    @Override
    public List<Pet> getAllByCriteria(PublicSearchCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = builder.createQuery(Pet.class);
        Root<Pet> root = criteriaQuery.from(Pet.class);

        List<Predicate> predicates = new ArrayList<>();

        //<!-- Наполнение листа предикатов
        Predicate genderPr = builder.equal(root.get("gender"), criteria.getGender());
        predicates.add(genderPr);

        //-->
        Order order = getOrderBySort(criteria.getPetSort(), builder, root);
        return getPets(criteriaQuery, root, predicates, order, criteria.getFrom(), criteria.getSize());
    }

    private Order getOrderBySort(PetSort petSort, CriteriaBuilder builder, Root<Pet> root) {
        Order order;
        switch (petSort) {
            case AGE_ASC:
                order = builder.desc(root.get("birth_date"));
                break;
            case AGE_DESC:
                order = builder.asc(root.get("birth_date"));
                break;
            case PRICE_ASC:
                order = builder.asc(root.get("price"));
                break;
            case PRICE_DESC:
                order = builder.desc(root.get("price"));
                break;
            default:
                order = builder.asc(root.get("id"));
        }
        return order;
    }

    private List<Pet> getPets(CriteriaQuery<Pet> criteriaQuery, Root<Pet> root, List<Predicate> predicates, Order order, int from, int size) {
        CriteriaQuery<Pet> select = criteriaQuery
                .select(root)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(order);
        TypedQuery<Pet> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }
}
