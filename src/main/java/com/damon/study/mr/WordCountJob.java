package com.damon.study.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 需求：读取hdfs上的hello.txt文件，计算文件中每个单词出现的总次数
 * hello.txt文件内容如下：
 * hello you
 * hello me
 * 最终需要的结果形式如下：
 * hello 2
 * me 1
 * you 1
 */
public class WordCountJob {

    public static void main(String[] args) {
        if (args.length != 2) {
            //  如果传递的参数不够，程序直接退出
            System.exit(100);
        }
        try {
            //  job需要的配置参数
            Configuration conf = new Configuration();
            //  创建一个job
            Job job = Job.getInstance(conf);

            //  注意：这一行必须设置，否则在集群中执行的是找不到WordCountJob这个类
            job.setJarByClass(WordCountJob.class);

            //  指定输入路径(可以是文件，也可以是目录)
            FileInputFormat.setInputPaths(job,new Path(args[0]));
            //  指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job,new Path(args[1]));

            //  指定map相关的代码
            job.setMapperClass(MyMapper.class);
            //  指定k2的类型
            job.setMapOutputKeyClass(Text.class);
            //  指定v2的类型
            job.setMapOutputValueClass(LongWritable.class);

            //  指定reduce相关的代码
            job.setReducerClass(MyReducer.class);
            //  指定k3的类型
            job.setOutputKeyClass(Text.class);
            //  指定v3的类型
            job.setOutputValueClass(LongWritable.class);

            //  提交job
            job.waitForCompletion(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        /**
         * 需要实现map函数
         * 这个map函数就是可以接收k1,v1， 产生k2,v2
         *
         * @param k1      文件的行号
         * @param v1      文件每行的内容
         *                <k1,v1>=<0,hello you>
         *                  k2:hello...v2:1
         *                  k2:you...v2:1
         *                <k1,v1>=<10,hello me>
         *                  k2:hello...v2:1
         *                  k2:me...v2:1
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            String[] words = v1.toString().split(" ");
            System.out.println("<k1,v1>=<"+k1.get()+","+v1.toString()+">");
            for (String word : words) {
                //  把迭代出来的单词封装成<k2,v2>的形式
                Text k2 = new Text(word);
                LongWritable v2 = new LongWritable(1L);
                System.out.println("k2:"+word+"...v2:1");
                //  把<k2,v2>写出去
                context.write(k2, v2);
            }
        }
    }

    /**
     * <k2,v2>=<hello,1>
     * <k2,v2>=<hello,1>
     * <k3,v3>=<hello,2>
     *
     * <k2,v2>=<me,1>
     * <k3,v3>=<me,1>
     *
     * <k2,v2>=<you,1>
     * <k3,v3>=<you,1>
     */
    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        /**
         * 针对v2s的数据进行累加求和 并且最终把数据转化为k3,v3写出去
         *
         * @param k2
         * @param v2s
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text k2, Iterable<LongWritable> v2s, Context context)
                throws IOException, InterruptedException {
            //  创建一个sum变量，保存v2s的和
            long sum = 0L;
            for (LongWritable v2 : v2s) {
                System.out.println("<k2,v2>=<"+k2.toString()+","+v2.get()+">");
                sum += v2.get();
            }
            //  组装k3,v3
            Text k3 = k2;
            LongWritable v3 = new LongWritable(sum);
            System.out.println("<k3,v3>=<"+k3.toString()+","+v3.get()+">");
            //  把结果写出去
            context.write(k3, v3);
        }
    }
}
