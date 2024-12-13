package org.mach.screenmatch.repository;

import org.mach.screenmatch.model.Categories;
import org.mach.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String title);

    List<Series> findByActorsContainingIgnoreCase(String inputName);

    List<Series> findTop5ByOrderByImdbRatingDesc();

    List<Series> findByGenre(Categories genre);
}