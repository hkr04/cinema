package com.influence.cm.controller;

import com.influence.cm.common.response.ResponseResult;
import com.influence.cm.domain.SysVote;
import com.influence.cm.service.impl.SysVoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SysVoteController extends BaseController {

    @Autowired
    private SysVoteServiceImpl sysVoteService;

    /**
     * 根据电影 ID 查询所有投票
     * @param movieId 电影 ID
     * @return 与指定电影 ID 关联的投票列表
     */
    @GetMapping("/sysVote/findByMovieId/{movieId}")
    public ResponseResult findVotesByContentId(@PathVariable Long movieId) {
        List<SysVote> data = sysVoteService.findVotesByMovieId(movieId);
        return getResult(data);
    }

    /**
     * 根据用户 ID 查询所有投票
     * @param userId 用户 ID
     * @return 与指定用户 ID 关联的投票列表
     */
    @GetMapping("/sysVote/findByUserId/{userId}")
    public ResponseResult findVotesByUserId(@PathVariable Long userId) {
        List<SysVote> data = sysVoteService.findVotesByUserId(userId);
        return getResult(data);
    }

    /**
     * 根据用户 ID 和 电影 ID 查询最新投票
     * @param userId
     * @param movieId
     * @return 最近投票结果
     */
    @GetMapping("/sysVote/findByUserMovieId")
    public ResponseResult findByUserMovieId(
            @RequestParam("userId") Long userId,
            @RequestParam("movieId") Long movieId) {
        // 根据用户 ID 获取该用户所有的投票记录
        List<SysVote> votes = sysVoteService.findVotesByUserId(userId);

        // 根据电影 ID 过滤出该用户对特定电影的投票
        Optional<SysVote> latestVote = votes.stream()
                .filter(vote -> vote.getMovieId().equals(movieId))
                .max((v1, v2) -> v1.getVoteTime().compareTo(v2.getVoteTime())); // 假设有 voteTime 字段

        // 如果找到了投票记录，则返回最新投票结果，否则返回空结果
        return getResult(latestVote.orElse(null));  // 如果没有找到，返回 null
    }

    /**
     * 添加新投票
     */
    @PostMapping("/sysVote")
    public ResponseResult addVote(@Validated @RequestBody SysVote sysVote) {
        System.out.println("接收到投票请求" + sysVote.toString());
        return getResult(sysVoteService.addVote(sysVote));
    }

}
