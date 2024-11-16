package com.influence.cm.controller;

import com.influence.cm.common.response.ResponseResult;
import com.influence.cm.domain.SysComment;
import com.influence.cm.domain.vo.SysCommentVo;
import com.influence.cm.service.impl.SysCommentServiceImpl;
import com.influence.cm.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysCommentController extends BaseController {
    @Autowired
    private SysCommentServiceImpl sysCommentService;

    // Find all comments (can be filtered based on SysCommentVo)
    @GetMapping("/sysComment/find")
    public ResponseResult findAllComments(SysCommentVo sysCommentVo) {
        startPage();
        List<SysComment> data = sysCommentService.findAllComments(sysCommentVo);
        return getResult(data);
    }

    // Find a specific comment by its ID
    @GetMapping("/sysComment/find/{id}")
    public ResponseResult findCommentById(@PathVariable Long id) {
        return getResult(sysCommentService.findCommentById(id));
    }

    // Add a new comment
    @PostMapping("/sysComment")
    public ResponseResult addComment(@Validated @RequestBody SysComment sysComment) {
        return getResult(sysCommentService.addComment(sysComment));
    }

    // Update an existing comment
    @PutMapping("/sysComment")
    public ResponseResult updateComment(@Validated @RequestBody SysComment sysComment) {
        return getResult(sysCommentService.updateComment(sysComment));
    }

    // Delete one or more comments by their IDs
    @DeleteMapping("/sysComment/{ids}")
    public ResponseResult deleteComment(@PathVariable Long[] ids) {
        return getResult(sysCommentService.deleteComment(ids));
    }
}