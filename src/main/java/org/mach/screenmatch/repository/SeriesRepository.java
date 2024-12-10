package org.mach.screenmatch.repository;

import org.mach.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Series findByTitle(String title);
}