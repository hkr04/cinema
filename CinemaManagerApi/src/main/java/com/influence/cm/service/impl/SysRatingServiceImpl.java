package com.influence.cm.service.impl;

import com.alibaba.druid.support.ibatis.SqlMapClientImplWrapper;
import com.influence.cm.domain.SysRating;
import com.influence.cm.mapper.SysRatingMapper;
import com.influence.cm.service.SysRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SysRatingServiceImpl implements SysRatingService {
    @Autowired
    private SysRatingMapper sysRatingMapper;

    @Override
    public int addRating(SysRating sysRating) {
        return sysRatingMapper.addRating(sysRating);
    }

    @Override
    public int updateRating(SysRating sysRating) {
        return sysRatingMapper.updateRating(sysRating);
    }

    /**
     * 根据用户ID查询所有评分
     * @param userId The ID of the user
     * @return A list of SysRating objects related to the user ID.
     */
    @Override
    public List<SysRating> findRatingsByUserId(Long userId) {
        return sysRatingMapper.findRatingsByUserId(userId);
    }

    /**
     * 根据电影ID查询所有评分
     * @param movieId The ID of the movie
     * @return A list of SysRating objects related to the movie ID.
     */
    @Override
    public List<SysRating> findRatingsByMovieId(Long movieId) {
        return sysRatingMapper.findRatingsByMovieId(movieId);
    }

    @Override
    public List<Integer> getRatingDistributionByMovieId(Long movieId) {
        // 创建一个列表，保存每个评分的数量
        List<Integer> ratingDistribution = new ArrayList<>(5);

        // 初始时，每个评分值的数量设置为 0
        for (int i = 0; i < 5; i++) {
            ratingDistribution.add(0); // 对应 1 分、2 分、3 分、4 分、5 分
        }

        // 通过 sysRatingMapper 查询电影的所有评分
        List<SysRating> ratings = sysRatingMapper.findRatingsByMovieId(movieId);

        // 遍历查询结果，统计每个评分的数量
        for (SysRating rating : ratings) {
            int ratingValue = (int)Math.ceil(rating.getRating() / 2.0);
            // 更新对应评分的数量
            ratingDistribution.set(ratingValue - 1, ratingDistribution.get(ratingValue - 1) + 1);
        }

        return ratingDistribution;
    }
}
