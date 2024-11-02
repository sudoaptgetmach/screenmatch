package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(Integer Season,
                         List<EpisodesData> Episodes) {
}
