package com.wu.rankinglist.utils;

import com.wu.rankinglist.model.enums.BType;

/**
 * 生成Redis的 key
 */
public class LikedUtil {


    /**
     * 生成点赞的key
     */
    public static String getKey(BType bType, Object subjectId) {
        return bType + ":" + subjectId;
    }
    /**
     * 生成点赞数量的key
     */
    public static String getReportKey(BType bType) {

        BType type = switch (bType) {
            case LIKED_ARTICLE -> BType.LIKED_ARTICLE_REPORT;
            case LIKED_ARTICLE_COMMENT, LIKED_VIDEO -> BType.LIKED_VIDEO_REPORT;
            case LIKED_VIDEO_COMMENT -> BType.LIKED_VIDEO_COMMENT_REPORT;
            //默认返回LIKED_ARTICLE_REPORT 文章数量key
            default -> BType.LIKED_ARTICLE_REPORT;
        };

        return type.name();
    }
}