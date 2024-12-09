package com.influence.cm.controller;

import com.influence.cm.common.response.ResponseResult;
import com.influence.cm.domain.SysComment;
import com.influence.cm.domain.vo.SysCommentVo;
import com.influence.cm.service.impl.SysCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysCommentController extends BaseController {

    @Autowired
    private SysCommentServiceImpl sysCommentService;

    /**
     * 查询所有评论（可根据 SysCommentVo 进行筛选）
     */
    @GetMapping("/sysComment/find")
    public ResponseResult findAllComments(SysCommentVo sysCommentVo) {
        startPage();
        List<SysComment> data = sysCommentService.findAllComments(sysCommentVo);
        return getResult(data);
    }

    /**
     * 根据内容 ID 查询所有评论
     * @param contentId 内容 ID（例如电影 ID）
     * @return 与指定内容 ID 关联的评论列表
     */
    @GetMapping("/sysComment/findByContentId/{contentId}")
    public ResponseResult findCommentsByContentId(@PathVariable Long contentId) {
        List<SysComment> data = sysCommentService.findCommentsByContentId(contentId);
        return getResult(data);
    }

    /**
     * 添加新评论
     */
    @PostMapping("/sysComment")
    public ResponseResult addComment(@Validated @RequestBody SysComment sysComment) {
        return getResult(sysCommentService.addComment(sysComment));
    }

    /**
     * 更新评论
     */
    @PutMapping("/sysComment")
    public ResponseResult updateComment(@Validated @RequestBody SysComment sysComment) {
        return getResult(sysCommentService.updateComment(sysComment));
    }

    /**
     * 根据 ID 删除一条或多条评论
     */
    @DeleteMapping("/sysComment/{ids}")
    public ResponseResult deleteComment(@PathVariable Long[] ids) {
        return getResult(sysCommentService.deleteComment(ids));
    }
}
