package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(
        @JsonAlias("Title") String title,
        @JsonAlias("Year") String year,
        @JsonAlias("Released") String released,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Actors") String actors,
        @JsonAlias("Plot") String plot,
        @JsonAlias("Language") String language,
        @JsonAlias("Poster") String poster,
        String imdbRating,
        Integer totalSeasons) {
}