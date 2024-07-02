package com.dti.ecim.event.repository;

import com.dti.ecim.event.entity.Event;
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
            return cb.equal(cb.lower(root.get("category").get("name")), category.toLowerCase());
        });
    }

    public static Specification<Event> byInterest(String interest) {
        return ((root, query, cb) -> {
            if (interest == null) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("interest").get("name")), interest.toLowerCase());
        });
    }

    public static Specification<Event> byCity(String city) {
        return ((root, query, cb) -> {
            if (city == null) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("location").get("city")), city.toLowerCase());
        });
    }

    public static Specification<Event> byState(String state) {
        return (((root, query, cb) -> {
            if (state == null) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("location").get("state")), state.toLowerCase());
        }));
    }
}