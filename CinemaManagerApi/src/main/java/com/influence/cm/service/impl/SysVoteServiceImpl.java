package com.influence.cm.service.impl;

import com.influence.cm.domain.SysVote;
import com.influence.cm.mapper.SysVoteMapper;
import com.influence.cm.service.SysVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysVoteServiceImpl implements SysVoteService {
    @Autowired
    private SysVoteMapper sysVoteMapper;

    @Override
    public int addVote(SysVote sysVote) {
        sysVote.setVoteTime(new Date());
        return sysVoteMapper.addVote(sysVote);
    }

    /**
     * 根据用户ID查询所有评分
     * @param userId The ID of the user
     * @return A list of SysVote objects related to the user ID.
     */
    @Override
    public List<SysVote> findVotesByUserId(Long userId) {
        return sysVoteMapper.findVotesByUserId(userId);
    }

    /**
     * 根据电影ID查询所有评分
     * @param movieId The ID of the movie
     * @return A list of SysVote objects related to the movie ID.
     */
    @Override
    public List<SysVote> findVotesByMovieId(Long movieId) {
        return sysVoteMapper.findVotesByMovieId(movieId);
    }
}
