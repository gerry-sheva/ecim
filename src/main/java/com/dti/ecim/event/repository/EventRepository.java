package com.dti.ecim.event.repository;

import com.dti.ecim.event.dao.SuggestionsDao;
import com.dti.ecim.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findAllByOrganizerIdOrderByEndingDate(Long organizerId, Pageable pageable);

    @Query(value = """
    SELECT e.id as id, e.title as title FROM event as e
    WHERE e.title ilike ?1
    """, nativeQuery = true)
    List<SuggestionsDao> findSuggestions(String search);
}
