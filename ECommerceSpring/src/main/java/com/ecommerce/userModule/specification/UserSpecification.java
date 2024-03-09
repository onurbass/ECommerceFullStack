package com.ecommerce.userModule.specification;

import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.util.common.BaseSpecification;
import com.ecommerce.util.common.SearchCriteria;
import lombok.Getter;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Component
public class UserSpecification extends BaseSpecification<User> {


    private List<SearchCriteria> criterias;

    public void setCriterias(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate
			(Root<User> root,CriteriaQuery<?> query,CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            Predicate predicate;
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.greaterThanOrEqualTo(

                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = builder.like(
                            builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");

                } else {
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase("=")) {


                if (criteria.getKey().equals("uuid")) {

                    Join<Role, User> userRole = root.join("roles");
                    predicate = builder.equal(userRole.get(criteria.getKey()), UUID.fromString(criteria.getValue().toString()));

                } else {
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }


            } else {
                continue;
            }

            predicates.add(predicate);

        }

        return builder.and(predicates.toArray(new Predicate[0]));

    }


}