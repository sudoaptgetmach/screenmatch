package org.mach.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer season;
    private String title;
    private Integer episode;
    private Double imdbRating;
    private LocalDate released;

    @ManyToOne
    private Series serie;

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

    public Episode() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Series getSerie() {
        return serie;
    }

    public void setSerie(Series serie) {
        this.serie = serie;
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
