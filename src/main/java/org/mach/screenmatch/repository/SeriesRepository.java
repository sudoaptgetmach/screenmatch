package org.mach.screenmatch.repository;

import org.mach.screenmatch.model.Categories;
import org.mach.screenmatch.model.Episode;
import org.mach.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String title);

    List<Series> findByActorsContainingIgnoreCase(String inputName);

    List<Series> findTop5ByOrderByImdbRatingDesc();

    List<Series> findByGenre(Categories genre);

    @Query("select s from Series s where s.totalSeasons >= :seasons and s.imdbRating >= :rating")
    List<Series> seriesBySeasonAndRating(@Param("seasons") Integer seasons, @Param("rating") String rating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE :excerptEpisode")
    List<Episode> EpisodeByExcerpt(String excerptEpisode);
}