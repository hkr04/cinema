package com.influence.cm.service.impl;

import com.influence.cm.domain.SysComment;
import com.influence.cm.domain.vo.SysCommentVo;
import com.influence.cm.mapper.SysCommentMapper;
import com.influence.cm.service.SysCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysCommentServiceImpl implements SysCommentService {

    @Autowired
    private SysCommentMapper sysCommentMapper;

    @Override
    public List<SysComment> findAllComments(SysCommentVo sysCommentVo) {
        return sysCommentMapper.findAllComments(sysCommentVo);
    }

    @Override
    public SysComment findOneComment(Long id) {
        return sysCommentMapper.findOneComment(id);
    }

    @Override
    public int addComment(SysComment sysComment) {
        return sysCommentMapper.addComment(sysComment);
    }

    @Override
    public int updateComment(SysComment sysComment) {
        return sysCommentMapper.updateComment(sysComment);
    }

    @Override
    public int deleteComment(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysCommentMapper.deleteComment(id);
        }
        return rows;
    }

    /**
     * 根据内容 ID 查询所有评论
     * @param contentId 内容 ID（例如电影 ID）
     * @return 评论列表
     */
    @Override
    public List<SysComment> findCommentsByContentId(Long contentId) {
        return sysCommentMapper.findCommentsByContentId(contentId);
    }

    @Override
    public List<SysComment> findByDateRange(Date startDate, Date endDate) {
        return sysCommentMapper.findByDateRange(startDate, endDate);
    }

    @Override
    public int getTotalCommentCount() {
        return sysCommentMapper.getTotalCommentCount();
    }
}
