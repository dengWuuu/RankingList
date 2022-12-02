package com.wu.数据结构手写.跳表;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Wu
 * @date 2022年12月02日 14:12
 */
public class Skiplist {
    static final int MAX_LEVEL = 32;
    static final double P_FACTOR = 0.25;
    private final SkipListNode head;
    private int level;
    private final Random random;

    public Skiplist() {
        this.head = new SkipListNode(-1, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }


    public boolean search(int target) {
        SkipListNode cur = head;
        //找到最小最接近target的节点
        for (int i = level - 1; i >= 0; i--) {
            while (cur.next[i] != null && cur.next[i].val < target) {
                cur = cur.next[i];
            }
        }

        cur = cur.next[0];
        return cur != null && cur.val == target;
    }

    public void add(int num) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL];
        Arrays.fill(update, head);

        SkipListNode cur = head;
        for (int i = level - 1; i >= 0; i--) {
            while (cur.next[i] != null && cur.next[i].val < num) {
                cur = cur.next[i];
            }
            update[i] = cur;
        }

        int level = this.randomLevel();
        this.level = Math.max(level, this.level);
        SkipListNode newNode = new SkipListNode(num, level);
        for (int i = 0; i < level; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
    }

    public boolean erase(int num) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL];
        SkipListNode cur = head;
        for (int i = level - 1; i >= 0; i--) {
            while (cur.next[i] != null && cur.next[i].val < num) {
                cur = cur.next[i];
            }
            update[i] = cur;
        }
        cur = cur.next[0];
        if (cur == null || cur.val != num) return false;

        for (int i = 0; i < level; i++) {
            if (update[i].next[i] != cur) break;
            update[i].next[i] = cur.next[i];
        }

        while (level > 1 && head.next[level - 1] == null) level--;
        return true;
    }

    private int randomLevel() {
        int lv = 1;
        /* 随机生成 lv */
        while (random.nextDouble() < P_FACTOR && lv < MAX_LEVEL) {
            lv++;
        }
        return lv;
    }

}

class SkipListNode {
    int val;
    SkipListNode[] next;

    public SkipListNode(int val, int level) {
        this.val = val;
        next = new SkipListNode[level];
    }
}
