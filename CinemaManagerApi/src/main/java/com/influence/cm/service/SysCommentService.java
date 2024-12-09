package com.influence.cm.service;

import com.influence.cm.domain.SysComment;
import com.influence.cm.domain.vo.SysCommentVo;

import java.util.Date;
import java.util.List;

public interface SysCommentService {

    /**
     * 查询所有评论
     * @param sysCommentVo The filter criteria for querying comments.
     * @return A list of SysComment matching the criteria.
     */
    List<SysComment> findAllComments(SysCommentVo sysCommentVo);

    /**
     * 根据ID查询评论
     * @param id The ID of the comment.
     * @return The SysComment object associated with the given ID.
     */
    // SysComment findCommentById(Long id);

    /**
     * 查询一个评论的信息，不查询相关的其他等信息
     * @param id The ID of the comment.
     * @return The SysComment object associated with the given ID.
     */
    SysComment findOneComment(Long id);

    /**
     * 添加评论
     * @param sysComment The SysComment object to be added.
     * @return The number of rows affected (should be 1 if successful).
     */
    int addComment(SysComment sysComment);

    /**
     * 更新评论
     * @param sysComment The SysComment object with updated information.
     * @return The number of rows affected (should be 1 if successful).
     */
    int updateComment(SysComment sysComment);

    /**
     * 根据ID删除评论
     * @param id The ID of the comment to be deleted.
     * @return The number of rows affected (should be 1 if successful).
     */
    int deleteComment(Long []id);

    /**
     * 根据内容ID查询所有评论（例如：电影的评论）
     * @param contentId The ID of the content (e.g., movie ID) for which comments are being queried.
     * @return A list of SysComment objects related to the content ID.
     */
    // List<SysComment> findByContentId(Long contentId);
    List<SysComment> findCommentsByContentId(Long contentId);


    /**
     * 根据日期范围查询评论
     * @param startDate The start date for the range.
     * @param endDate The end date for the range.
     * @return A list of SysComment objects created within the specified date range.
     */
    List<SysComment> findByDateRange(Date startDate, Date endDate);

    /**
     * 获取评论的总数量
     * @return The total count of comments in the system.
     */
    int getTotalCommentCount();
}
