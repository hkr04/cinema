package com.influence.cm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SysRating implements Serializable {

    private static final long serialVersionUID = 1L;

    // Rating ID
    private Long ratingId;

    // The ID of the movie being rated on
    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    // The user of the rating
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    // The content of the rating
    @NotNull(message = "Rating cannot be null")
    private int rating;

    public SysRating() {
    }

    public SysRating(Long ratingId, Long movieID, Long userId, int rating) {
        this.ratingId = ratingId;
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRating that = (SysRating) o;
        return Objects.equals(ratingId, that.ratingId) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, movieId, userId, rating);
    }

    @Override
    public String toString() {
        return "SysRating{" +
                "ratingId=" + ratingId +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", rating='" + rating +
                '}';
    }
}
