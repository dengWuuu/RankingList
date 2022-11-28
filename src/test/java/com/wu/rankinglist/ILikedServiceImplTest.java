package com.wu.rankinglist;

import com.wu.rankinglist.model.entity.Article;
import com.wu.rankinglist.model.entity.User;
import com.wu.rankinglist.model.enums.BType;
import com.wu.rankinglist.service.ILikedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ILikedServiceImplTest {
    @Autowired
    ILikedService iLikedService;


    @Test
    public void liked() {

        //文章1、文章2、文章3分被点赞

        Article article1 = new Article().setArticleId(1000).setTitle("文章1").setSummary("aaaaaaaaaaaaa").setImg("图");
        Article article2 = new Article().setArticleId(1001).setTitle("文章2").setSummary("bbbbbbbbbbbbb").setImg("图");
        Article article3 = new Article().setArticleId(1002).setTitle("文章3").setSummary("ccccccccccccc").setImg("图");

        //对id为 1000点赞
        iLikedService.liked(BType.LIKED_ARTICLE, 1000, 1, new User().setId((long) 1).setName("A").setAge(22), article1);

        //对id为 1001点赞
        iLikedService.liked(BType.LIKED_ARTICLE, 1001, 2, new User().setId((long) 2).setName("B").setAge(23), article2);
        iLikedService.liked(BType.LIKED_ARTICLE, 1001, 3, new User().setId((long) 3).setName("B").setAge(24), article2);


        //对id为 1002点赞
        iLikedService.liked(BType.LIKED_ARTICLE, 1002, 1, new User().setId((long) 1).setName("C").setAge(24), article3);
        iLikedService.liked(BType.LIKED_ARTICLE, 1002, 2, new User().setId((long) 2).setName("C").setAge(24), article3);
        iLikedService.liked(BType.LIKED_ARTICLE, 1002, 3, new User().setId((long) 3).setName("C").setAge(24), article3);

    }


    @Test
    public void likedCancel() {

        //取消id为2的用户对LIKED_ARTICLE文章id为1000的点赞
        Article article1 = new Article().setArticleId(1000).setTitle("文章1").setSummary("aaaaaaaaaaaaa").setImg("图");
        iLikedService.liked(BType.LIKED_ARTICLE, 1000, 0, new User().setId((long) 0).setName("A").setAge(22), article1);

    }


    @Test
    public void count() {

        Long count = iLikedService.count(BType.LIKED_ARTICLE, 1000);
        System.out.println(count);

    }

    @Test
    public void getSubjectTopN() {

        Set<Object> subjectTopN = iLikedService.getSubjectTopN(BType.LIKED_ARTICLE, 100L);

        System.out.println(subjectTopN);

    }
}