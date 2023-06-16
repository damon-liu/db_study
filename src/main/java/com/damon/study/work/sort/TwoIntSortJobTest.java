package com.damon.study.work.sort;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class TwoIntSortJobTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("3 3", "3 1", "3 2","2 1", "2 2", "1 1");
        List<TwoIntWritable> v2 = Lists.newArrayList();
        for (String s : list) {
            String[] fields = s.split(" ");
            TwoIntWritable key2 = new TwoIntWritable();
            key2.setFirstParam(Integer.parseInt(fields[0]));
            key2.setSecondParam(Integer.parseInt(fields[1]));
            v2.add(key2);
        }
        Object[] a = v2.toArray();
        Arrays.sort(a);
        for (Object o : a) {
            TwoIntWritable t = (TwoIntWritable) o;
            String k3 = String.format("%d->%d", t.getFirstParam(),  t.getSecondParam());
            System.out.println(k3);
        }
    }
}
