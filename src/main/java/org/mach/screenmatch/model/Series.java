package org.mach.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Series {

    private String title;
    private String year;
    private String released;
    private String genre;
    private String actors;
    private String plot;
    private String language;
    private String poster;
    private String imdbRating;
    private Integer totalSeasons;

    public Series(SeriesData data) {
        this.title = data.title();
        this.year = data.year();
        this.released = data.released();
        this.genre = data.genre();
        this.actors = data.actors();
        this.plot = data.plot();
        this.language = data.language();
        this.poster = data.poster();
        this.imdbRating = String.valueOf(OptionalDouble.of(Double.parseDouble(data.imdbRating())).orElse(0));
        this.totalSeasons = data.totalSeasons();
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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
                "totalSeasons = " + totalSeasons;
    }
}
