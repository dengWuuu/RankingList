package com.wu.rankinglist.service.impl;

import com.wu.rankinglist.model.entity.Article;
import com.wu.rankinglist.model.enums.BType;
import com.wu.rankinglist.service.ILikedService;
import com.wu.rankinglist.utils.LikedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ILikedServiceImpl implements ILikedService {


    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;
    private final ZSetOperations<String, Object> zSetOperations;

    public ILikedServiceImpl(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = this.redisTemplate.opsForHash();
        zSetOperations = this.redisTemplate.opsForZSet();

    }

    /**
     * 点赞/取消点赞
     *
     * @param bType     业务类型
     * @param subjectId 被点赞主体ID
     * @param postId    点赞主体ID
     */
    @Override
    public void liked(BType bType, Object subjectId, Object postId, Object user, Object subject) {
        //生成点赞key
        String key = LikedUtil.getKey(bType, subjectId);
        //判断key对应的set中是否存在subjectId
        log.debug("key:{} ,  postId:{}", key, postId);
        //存在则取消点赞
        Boolean aBoolean = hashOperations.hasKey(key, postId.toString());
        log.debug("此文章点赞情况:{}", aBoolean);
        if (aBoolean) {
            hashOperations.delete(key, postId.toString());
        } else {
            //不存在则点赞
            hashOperations.put(key, postId.toString(), user);
        }

        //生成点赞统计key
        String reportKey = LikedUtil.getReportKey(bType);
        if (aBoolean) {
            //已经存在赞
            //需要取消点赞，设置分数为 -1 ， 最低分数
            zSetOperations.incrementScore(reportKey, subject, -1);
        } else {
            Double score = zSetOperations.score(reportKey, subject);
            if (score == null) {
                //score分数不存在则设置 score为1
                zSetOperations.add(reportKey, subject, 1);
            } else {
                //score分数存在则 zset的score分数加1
                zSetOperations.incrementScore(reportKey, subject, 1);
            }
        }

    }

    /**
     * 查询单个主体（如文章）的获赞个数，如 id为 1的文章被点赞的数量
     *
     * @param bType     业务类型
     * @param subjectId 被点赞主体ID
     * @return 点赞数量
     */
    @Override
    public Long count(BType bType, Object subjectId) {
        //生成key
        String key = LikedUtil.getKey(bType, subjectId);
        //获得点赞数量
        return hashOperations.size(key);
    }

    @Override
    public Set<Object> getSubjectTopN(BType bType, Long top) {

        //获得点赞主体排行版
        Set<Object> set = zSetOperations.reverseRange(LikedUtil.getReportKey(bType), 0, top);

        //得到以bType开头的所有key组成的集合
        Set<String> keys = redisTemplate.keys(bType + ":*");

        if (set == null) return null;
        if (keys == null) return null;
        //给返回结果集设置 点赞数
        for (Object obj : set) {
            for (String key : keys) {
                Integer id = Integer.valueOf(key.substring(key.lastIndexOf(":") + 1));
                if (((Article) obj).getArticleId().equals(id)) {
                    ((Article) obj).setLikeNum(hashOperations.size(key).intValue());
                }
            }
        }
        return set;
    }


}

