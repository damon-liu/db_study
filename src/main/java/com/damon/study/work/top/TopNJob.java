package com.damon.study.work.top;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 使用MapReduce程序从一批数据中计算出数值最大的前N个数字。
 * 2, 1, 10, 4, 8, 5, 7, 6, 3, 9
 * 输入：N=5
 * 希望的输出数据：10 9 8 7 6
 *
 * hadoop jar db_study-1.0-SNAPSHOT-jar-with-dependencies.jar com.damon.study.work.top.TopNJob  /test/topN.txt /topN/out0 6
 */
public class TopNJob {

    public static void main(String[] args) {
        if (args.length != 3) {
            //  如果传递的参数不够，程序直接退出
            System.exit(100);
        }
        String filePath = args[0];
        String outPath = args[1];
        String topN = args[2];

        try {
            //  job需要的配置参数
            Configuration conf = new Configuration();
            conf.set("topN", topN);

            //  创建一个job
            Job job = Job.getInstance(conf);

            //  注意：这一行必须设置，否则在集群中执行的是找不到WordCountJob这个类
            job.setJarByClass(TopNJob.class);

            //  指定输入路径(可以是文件，也可以是目录)
            FileInputFormat.setInputPaths(job, new Path(filePath));
            //  指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job, new Path(outPath));

            //  指定map相关的代码
            job.setMapperClass(TopNJob.MyMapper.class);
            //  指定k2的类型
            job.setMapOutputKeyClass(LongWritable.class);
            //  指定v2的类型
            job.setMapOutputValueClass(NullWritable.class);

            //  指定reduce相关的代码
            job.setReducerClass(TopNJob.MyReducer.class);
            //  指定k3的类型
            job.setOutputKeyClass(Text.class);
            //  指定v3的类型
            job.setOutputValueClass(NullWritable.class);

            //  提交job
            job.waitForCompletion(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class MyMapper extends Mapper<LongWritable, Text, LongWritable, NullWritable> {
        private NullWritable value = NullWritable.get();

        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            System.out.println("<k1,v1>=<" + k1.get() + "," + v1.toString() + ">");
            LongWritable k2 = new LongWritable(Long.parseLong(v1.toString()));
            //  把<k2,v2>写出去
            System.out.println("k2: " + k2);
            context.write(k2, value);
        }
    }

    public static class MyReducer extends Reducer<LongWritable, NullWritable, LongWritable, NullWritable> {

        List<Integer> lst = new ArrayList<>();

        Integer topN = 5;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            this.topN = Math.toIntExact(context.getConfiguration().getLong("topN", 5));
        }

        @Override
        protected void reduce(LongWritable k2, Iterable<NullWritable> v2s, Context context) throws IOException, InterruptedException {
            System.out.println("reduce lst: " + lst);
            lst.add((int) k2.get());
        }

        @Override
        protected void cleanup(Reducer<LongWritable, NullWritable, LongWritable, NullWritable>.Context context) throws IOException, InterruptedException {
            System.out.println("cleanup lst: " + lst);
            Collections.sort(lst, Collections.reverseOrder());
            for (int i = 0; i < topN; i++) {
                context.write(new LongWritable(lst.get(i)), NullWritable.get());
            }
        }
    }
}
