package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesData(String Title,
                           Integer Episode,
                           String imdbRating,
                           String Released) {
}
