package com.influence.cm.mapper;

import com.influence.cm.domain.SysVote;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysVoteMapper {
    /**
     * 添加投票
     * @param sysVote The SysVote object to be added.
     * @return The number of rows affected (should be 1 if successful).
     */
    int addVote(SysVote sysVote);


    /**
     * 根据用户ID查询所有投票
     * @param userId The ID of the user
     * @return A list of SysVote objects related to the user ID.
     */
    List<SysVote> findVotesByUserId(Long userId);

    /**
     * 根据电影ID查询所有投票
     * @param movieId The ID of the movie
     * @return A list of SysVote objects related to the movie ID.
     */
    List<SysVote> findVotesByMovieId(Long movieId);
}
