package org.mach.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {

    private final Integer season;
    private final String title;
    private final Integer episode;
    private Double imdbRating;
    private LocalDate released;

    public Episode(Integer season, EpisodesData d) {
        this.season = season;
        this.title = d.title();
        this.episode = d.episode();

        try {
            this.imdbRating = Double.valueOf(d.imdbRating());
            this.released = LocalDate.parse(d.released());
        } catch (NumberFormatException e) {
            this.imdbRating = (double) 0;
        } catch (DateTimeParseException e) {
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public LocalDate getReleased() {
        return released;
    }

    @Override
    public String toString() {
        return  "  temporada=" + season +
                ", titulo='" + title + '\'' +
                ", episódio=" + episode +
                ", avaliacao=" + imdbRating +
                ", data de lançamento=" + released;
    }
}
