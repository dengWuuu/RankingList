package com.wu.rankinglist.service;

import com.wu.rankinglist.model.enums.BType;

import java.util.Set;

/**
 * ILikedService 点赞接口
 * @author: Wu
 */
public interface ILikedService {

    /**
     * 点赞/取消点赞
     *
     * @param bType     业务类型
     * @param subjectId 被点赞主体ID
     * @param postId    点赞主体ID
     * @param user      点赞用户
     * @param subject   被点赞主体
     */
    void liked(BType bType, Object subjectId, Object postId, Object user, Object subject);

    /**
     * 查询单个主体（如文章）的获赞个数，如 id为 1的文章被点赞的数量
     *
     * @param bType     实体类型
     * @param subjectId 被点赞主体ID
     * @return 点赞数量
     */
    Long count(BType bType, Object subjectId);

    /**
     * 获得获得点赞数 前top名的bType排行版
     *
     * @param bType 实体类型
     * @return top 排行版截取数
     */
    Set<Object> getSubjectTopN(BType bType, Long top);

}