package com.influence.cm.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SysVote implements Serializable {

    private static final long serialVersionUID = 1L;

    // Rating ID
    private Long voteId;

    // The ID of the movie being rated on
    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    // The user of the vote
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Start time cannot be null")
    private String startTime;

    @NotNull(message = "End time cannot be null")
    private String endTime;

    private Date voteTime;

    public SysVote() {
    }

    public SysVote(Long voteId, Long movieId, Long userId, String startTime, String endTime, Date voteTime) {
        this.voteId = voteId;
        this.movieId = movieId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.voteTime = voteTime;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysVote that = (SysVote) o;
        return Objects.equals(voteId, that.voteId) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(voteTime, that.voteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteId, movieId, userId, startTime, endTime, voteTime);
    }

    @Override
    public String toString() {
        return "SysRating{" +
                "voteId=" + voteId +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", voteTime=" + voteTime +
                '}';
    }
}
