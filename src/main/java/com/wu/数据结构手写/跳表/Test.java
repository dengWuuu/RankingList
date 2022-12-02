package com.wu.数据结构手写.跳表;

/**
 * @author Wu
 * @date 2022年12月02日 14:53
 */
public class Test {
    public static void main(String[] args) {
        Skiplist skipList = new Skiplist();
        skipList.add(5);
        skipList.add(1);
        skipList.add(4);


        System.out.println(skipList.search(4));

        System.out.println(skipList.erase(5));
        System.out.println(skipList.search(5));
    }
}
