package com.wu.rankinglist.controller;

import com.wu.rankinglist.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author Wu
 * @date 2022年11月16日 17:11
 */
@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class RankController {
    private static final String PREFIX = "list";
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取所有人排名
     *
     * @return
     */
    @GetMapping("/list")
    public List<Person> getRank() {
        BoundZSetOperations<String, String> ZSet = stringRedisTemplate.boundZSetOps(PREFIX);
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();

        Set<String> strings = ZSet.reverseRangeByLex(range);
        List<String> string = null;
        if (strings != null) string = strings.stream().toList();
        assert string != null;
        log.info(string.toString());
        return null;
    }

    /**
     * 加一个人的分数和名字
     *
     * @return
     */
    @PostMapping("/add")
    public String addPerson(Person person) {
        BoundZSetOperations<String, String> ZSet = stringRedisTemplate.boundZSetOps(PREFIX);
        ZSet.add(person.getName(), person.getScore());
        return "success";
    }

}
