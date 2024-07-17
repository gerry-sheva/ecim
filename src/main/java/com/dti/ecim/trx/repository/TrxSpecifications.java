package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.enums.TimeSpecifier;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class TrxSpecifications {
    public static Specification<Trx> byTime(Instant date, TimeSpecifier timeSpecifier) {
        if (timeSpecifier == TimeSpecifier.DAY) {
            return (root, query, cb) -> cb.equal(cb.function("DATE", Instant.class, root.get("createdAt")), date);
        } else if (timeSpecifier == TimeSpecifier.WEEK) {
            return (root, query, cb) -> {
                Expression<Instant> truncatedDate = cb.function("date_trunc", Instant.class, cb.literal("week"), root.get("createdAt"));
                Expression<Instant> truncatedParam = cb.function("date_trunc", Instant.class, cb.literal("week"), cb.literal(date));
                return cb.equal(truncatedDate, truncatedParam);
            };
        } else if (timeSpecifier == TimeSpecifier.MONTH) {
            return (root, query, cb) -> {
                Expression<Instant> truncatedDate = cb.function("date_trunc", Instant.class, cb.literal("month"), root.get("createdAt"));
                Expression<Instant> truncatedParam = cb.function("date_trunc", Instant.class, cb.literal("month"), cb.literal(date));
                return cb.equal(truncatedDate, truncatedParam);
            };
        } else if (timeSpecifier == TimeSpecifier.YEAR) {
            return (root, query, cb) -> {
                Expression<Instant> truncatedDate = cb.function("date_trunc", Instant.class, cb.literal("year"), root.get("createdAt"));
                Expression<Instant> truncatedParam = cb.function("date_trunc", Instant.class, cb.literal("year"), cb.literal(date));
                return cb.equal(truncatedDate, truncatedParam);
            };
        } else if (timeSpecifier == TimeSpecifier.LIFETIME) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }

    public static Specification<Trx> byOrganizerId(Long organizerId) {
        return (root, query, cb) -> cb.equal(root.get("organizerId"), organizerId);
    }

    public static Specification<Trx> byAttendeeId(Long attendeeId) {
        return ((root, query, cb) -> cb.equal(root.get("attendeeId"), attendeeId));
    }

    public static Specification<Trx> sumFinalPrice() {
        return (root, query, cb) -> {
            // Check if we're in a count query
            if (query.getResultType() == Long.class) {
                return null;
            }

            // Create a subquery
            Subquery<Number> subquery = query.subquery(Number.class);
            Root<Trx> subRoot = subquery.from(Trx.class);

            // Select the sum of finalPrice
            subquery.select(cb.sum(subRoot.get("finalPrice")));

            // Compare the subquery result with a literal value
            return cb.greaterThan(subquery.getSelection().as(Integer.class), 0);
        };
    }
}
