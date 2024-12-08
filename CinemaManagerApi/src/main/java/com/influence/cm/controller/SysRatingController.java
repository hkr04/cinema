package com.influence.cm.controller;

import com.influence.cm.common.response.ResponseResult;
import com.influence.cm.domain.SysRating;
import com.influence.cm.service.impl.SysRatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysRatingController extends BaseController {

    @Autowired
    private SysRatingServiceImpl sysRatingService;

    /**
     * 根据电影 ID 查询所有评分
     * @param movieId 电影 ID
     * @return 与指定电影 ID 关联的评分列表
     */
    @GetMapping("/sysRating/findByMovieId/{movieId}")
    public ResponseResult findRatingsByContentId(@PathVariable Long movieId) {
        List<SysRating> data = sysRatingService.findRatingsByMovieId(movieId);
        return getResult(data);
    }

    /**
     * 根据用户 ID 查询所有评分
     * @param userId 用户 ID
     * @return 与指定用户 ID 关联的评分列表
     */
    @GetMapping("/sysRating/findByUserId/{userId}")
    public ResponseResult findRatingsByUserId(@PathVariable Long userId) {
        List<SysRating> data = sysRatingService.findRatingsByUserId(userId);
        return getResult(data);
    }

    /**
     * 添加新评分
     */
    @PostMapping("/sysRating")
    public ResponseResult addRating(@Validated @RequestBody SysRating sysRating) {
        return getResult(sysRatingService.addRating(sysRating));
    }

    /**
     * 更新评分
     */
    @PutMapping("/sysRating")
    public ResponseResult updateRating(@Validated @RequestBody SysRating sysRating) {
        return getResult(sysRatingService.updateRating(sysRating));
    }
}
