package org.mach.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String year;
    private String released;

    @Enumerated(EnumType.STRING)
    private Categories genre;

    private String actors;
    private String plot;
    private String language;
    private String poster;
    private String imdbRating;
    private Integer totalSeasons;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes = new ArrayList<>();

    public Series() {}

    public Series(SeriesData data) {
        this.title = data.title();
        this.year = data.year().split("-")[0].trim();
        this.released = data.released();
        this.genre = Categories.fromString(data.genre().split(",")[0].trim());
        this.actors = data.actors();
        this.plot = data.plot();
        this.language = data.language();
        this.poster = data.poster();
        this.imdbRating = String.valueOf(OptionalDouble.of(Double.parseDouble(data.imdbRating())).orElse(0));
        this.totalSeasons = data.totalSeasons();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Categories getGenre() {
        return genre;
    }

    public void setGenero(Categories genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(e -> e.setSerie(this));
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "title = " + title + "\n" +
                "year = " + year + "\n" +
                "released = " + released + "\n" +
                "genre = " + genre + "\n" +
                "actors = " + actors + "\n" +
                "plot = " + plot + "\n" +
                "language = " + language + "\n" +
                "poster = " + poster + "\n" +
                "imdbRating = " + imdbRating + "\n" +
                "totalSeasons = " + totalSeasons + "\n" +
                "episodes: " + episodes + "\n";
    }
}