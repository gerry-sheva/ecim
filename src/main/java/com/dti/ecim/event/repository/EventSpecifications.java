package com.dti.ecim.event.repository;

import com.dti.ecim.event.entity.Event;
import com.dti.ecim.metadata.entity.Category;
import com.dti.ecim.metadata.entity.Interest;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {
    public static Specification<Event> byTitle(String title) {
        return ((root, query, cb) -> {
            if (title == null) {
                return cb.conjunction();
            }
           return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        });
    }

    public static Specification<Event> byCategory(String category) {
        return ((root, query, cb) -> {
            if (category == null) {
                return cb.conjunction();
            }
//            Join<Event, Category> join = root.join("category");
//            return cb.equal(join.get("category_id"), category);
            return cb.equal(cb.lower(root.get("category").get("name")), category.toLowerCase());
        });
    }

    public static Specification<Event> byInterest(String interest) {
        return ((root, query, cb) -> {
            if (interest == null) {
                return cb.conjunction();
            }
//            Join<Event, Interest> join = root.join("interest");
//            return cb.equal(join.get("interest_id"), interest);
            return cb.equal(cb.lower(root.get("interest").get("name")), interest.toLowerCase());
        });
    }
}
