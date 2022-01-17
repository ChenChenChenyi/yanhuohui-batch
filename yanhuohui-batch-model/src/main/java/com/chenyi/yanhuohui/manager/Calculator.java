package com.chenyi.yanhuohui.manager;

/**
 * 学习单元测试使用到的实体类
 */
public class Calculator {
    private int sumXX(int a, int b) {
        System.out.println("sumXX");
        return a + b;
    }

    public int callSumXX(int a, int b) {
        System.out.println("callSumXX");
        return  sumXX(a, b);
    }
}
