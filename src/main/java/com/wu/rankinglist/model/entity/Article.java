package com.wu.rankinglist.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article implements Serializable {
    //文章id
    private Integer articleId;
    //文章标题
    private String title;
    //文章摘要
    private String summary;
    //文章图片
    private String img;

    //点赞数，该字段只用于排行版统计
    private Integer likeNum;
}