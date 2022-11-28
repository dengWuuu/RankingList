package com.wu.rankinglist.model.enums;

/**
 * BType 点赞主体的枚举类型
 * @author: Wu
 */
public enum BType {
    //点赞
    LIKED_ARTICLE("文章点赞"),
    LIKED_ARTICLE_COMMENT("文章评论点赞"),
    LIKED_VIDEO("视频点赞"),
    LIKED_VIDEO_COMMENT("视频评论点赞"),

    //点赞统计
    LIKED_ARTICLE_REPORT("文章点赞统计"),
    LIKED_ARTICLE_COMMENT_REPORT("文章评论点赞统计"),
    LIKED_VIDEO_REPORT("视频点赞统计"),
    LIKED_VIDEO_COMMENT_REPORT("视频评论点赞统计");

    private String bType;

    BType(String bType) {
        this.bType = bType;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }
}