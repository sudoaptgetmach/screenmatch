package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesData(@JsonAlias("Title") String title,
                           @JsonAlias("Episode") Integer episode,
                           String imdbRating,
                           @JsonAlias("Released") String released) {
}
