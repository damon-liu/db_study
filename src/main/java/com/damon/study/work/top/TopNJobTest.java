package com.damon.study.work.top;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class TopNJobTest {

    public static void main(String[] args) {
        int n = 6;
        List<Integer> list = Lists.newArrayList(2, 1, 10, 4, 8, 5, 7, 6, 3, 9);
        Object[] a = list.toArray();
        Arrays.sort(a);
        int i = a.length;
        if(i < n){
            System.out.println("参数错误");
        }
        for (int i1 = list.size() ; i1 > i - n; i1--) {
            System.out.println(i1);
        }
    }
}
