package com.quarkushackfest.team6.domain;

import java.util.Objects;

public class Quote {
    private String movieId;
    private String movie;
    private String plot;

    public Quote() {

    }

    public Quote(String movieId, String movie, String plot) {
        this.movieId = movieId;
        this.movie = movie;
        this.plot = plot;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Quote{");
        sb.append("movieId='").append(movieId).append('\'');
        sb.append(", movie='").append(movie).append('\'');
        sb.append(", plot='").append(plot).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(movieId, quote.movieId) &&
                Objects.equals(movie, quote.movie) &&
                Objects.equals(plot, quote.plot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movie, plot);
    }
}
