package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(String Title,
                         String Year,
                         String Released,
                         String Genre,
                         String Actors,
                         String Plot,
                         String Language,
                         String imdbRating,
                         Integer totalSeasons) {
}
