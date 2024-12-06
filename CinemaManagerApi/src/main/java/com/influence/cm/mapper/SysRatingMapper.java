package com.influence.cm.mapper;

import com.influence.cm.domain.SysRating;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysRatingMapper {
    /**
     * 添加评分
     * @param sysRating The SysRating object to be added.
     * @return The number of rows affected (should be 1 if successful).
     */
    int addRating(SysRating sysRating);

    /**
     * 更新评分
     * @param sysRating The SysRating object with updated information.
     * @return The number of rows affected (should be 1 if successful).
     */
    int updateRating(SysRating sysRating);

    /**
     * 根据用户ID查询所有评分
     * @param userId The ID of the user
     * @return A list of SysRating objects related to the user ID.
     */
    List<SysRating> findRatingsByUserId(Long userId);

    /**
     * 根据电影ID查询所有评分
     * @param movieId The ID of the movie
     * @return A list of SysRating objects related to the movie ID.
     */
    List<SysRating> findRatingsByMovieId(Long movieId);


    /**
     * 获取评分的分布
     * @param movieId The ID of the movie
     * @return The distribution of ratings  of the movie in the system.
     */
    List<Integer> getRatingDistributionByMovieId(Long movieId);
}
