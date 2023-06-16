package com.damon.study.work.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TwoIntWritable implements WritableComparable<TwoIntWritable> {

    private Integer  firstParam;

    private Integer secondParam;

    public TwoIntWritable() {
        firstParam = 0;
        secondParam = 0;
    }

    @Override
    public int compareTo(TwoIntWritable o) {
        int i = Integer.compare(firstParam,  o.firstParam);
        if (i != 0) {
            return i;
        } else {
            return Integer.compare(o.secondParam, secondParam);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(firstParam);
        out.writeInt(secondParam);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        firstParam = in.readInt();
        secondParam = in.readInt();
    }

    public Integer getFirstParam() {
        return firstParam;
    }

    public void setFirstParam(Integer firstParam) {
        this.firstParam = firstParam;
    }

    public Integer getSecondParam() {
        return secondParam;
    }

    public void setSecondParam(Integer secondParam) {
        this.secondParam = secondParam;
    }
}
