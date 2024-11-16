package com.influence.cm.service.impl;

import com.influence.cm.domain.SysComment;
import com.influence.cm.domain.vo.SysCommentVo;
import com.influence.cm.mapper.SysCommentMapper;
import com.influence.cm.service.SysCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class SysCommentServiceImpl implements SysCommentService {

    @Autowired
    private SysCommentMapper sysCommentMapper;

    @Override
    public List<SysComment> findAllComments(SysCommentVo sysCommentVo) {
        // Find comments based on the filter conditions provided in SysCommentVo
        return sysCommentMapper.findAllComments(sysCommentVo);
    }

    @Override
    public SysComment findCommentById(Long id) {
        // Find a specific comment by its ID
        return sysCommentMapper.findCommentById(id);
    }

    @Override
    public SysComment findOneComment(Long id) {
        // Find one comment by its ID (could be a more specific search if necessary)
        return sysCommentMapper.findOneComment(id);
    }

    @Override
    public int addComment(SysComment sysComment) {
        // Add a new comment to the database
        return sysCommentMapper.addComment(sysComment);
    }

    @Override
    public int updateComment(SysComment sysComment) {
        // Update an existing comment
        return sysCommentMapper.updateComment(sysComment);
    }

    @Override
    public int deleteComment(Long[] ids) {
        // Delete comments by their IDs
        int rows = 0;
        for (Long id : ids) {
            rows += sysCommentMapper.deleteComment(id);
        }
        return rows;
    }

    /**
     * Get a list of comments for a specific content (e.g., a movie or post).
     * 
     * @param contentId The ID of the content (movie, post, etc.) the comments are associated with.
     * @return A list of comments associated with the given content ID.
     */
    @Override
    public List<SysComment> findByContentId(Long contentId) {
        return sysCommentMapper.findByContentId(contentId);
    }

    /**
     * Get comments within a specific date range (for example, get comments from the past week).
     * 
     * @param startDate The start date for filtering comments.
     * @param endDate The end date for filtering comments.
     * @return A list of comments within the specified date range.
     */
    @Override
    public List<SysComment> findByDateRange(Date startDate, Date endDate) {
        return sysCommentMapper.findByDateRange(startDate, endDate);
    }

    /**
     * Get the total number of comments.
     * 
     * @return The total count of comments in the database.
     */
    @Override
    public int getTotalCommentCount() {
        return sysCommentMapper.getTotalCommentCount();
    }
}
