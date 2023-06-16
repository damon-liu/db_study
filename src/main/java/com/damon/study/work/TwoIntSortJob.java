package com.damon.study.work;

import com.damon.study.mr.WordCountJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * hadoop jar db_study-1.0-SNAPSHOT-jar-with-dependencies.jar com.damon.study.work.TwoIntSortJob  /test/sort.txt /out4
 */
public class TwoIntSortJob {

    public static void main(String[] args) {
        String k3 = String.format("%d->%d", 1,
              0);
        System.out.println(k3);
        // if (args.length != 2) {
        //     //  如果传递的参数不够，程序直接退出
        //     System.exit(100);
        // }
        String filePath = args[0];
        String outPath = args[1];

        try {
            //  job需要的配置参数
            Configuration conf = new Configuration();
            //  创建一个job
            Job job = Job.getInstance(conf);

            //  注意：这一行必须设置，否则在集群中执行的是找不到WordCountJob这个类
            job.setJarByClass(TwoIntSortJob.class);

            //  指定输入路径(可以是文件，也可以是目录)
            FileInputFormat.setInputPaths(job,new Path(filePath));
            //  指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job,new Path(outPath));

            //  指定map相关的代码
            job.setMapperClass(TwoIntSortJob.MyMapper.class);
            //  指定k2的类型
            job.setMapOutputKeyClass(TwoIntWritable.class);
            //  指定v2的类型
            job.setMapOutputValueClass(NullWritable.class);

            //  指定reduce相关的代码
            job.setReducerClass(TwoIntSortJob.MyReducer.class);
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

    public static class MyMapper extends Mapper<LongWritable, Text, TwoIntWritable, NullWritable> {
        private TwoIntWritable key2 = new TwoIntWritable();
        private NullWritable value = NullWritable.get();
        private final static IntWritable firstCol = new IntWritable();
        private final static IntWritable secondCol = new IntWritable();

        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            System.out.println("<k1,v1>=<"+k1.get()+","+v1.toString()+">");
            String[] fields = v1.toString().split(" ");
            if (fields.length == 2) {
                firstCol.set(Integer.parseInt(fields[0])); // 将第一列解析为整型数据，并存入FirstCol变量中。
                secondCol.set(Integer.parseInt(fields[1])); // 将第二列解析为整型数据，并存入SecondCol变量中。
                key2.setFirstParam(firstCol.get()); // 将FirstCol变量封装到TwoIntWritable键中。
                key2.setSecondParam(secondCol.get()); // 将SecondCol变量封装到TwoIntWritable键中。
                //  把<k2,v2>写出去
                System.out.println("k2: "+ " firstParam: " + key2.getFirstParam() + " secondParam: " + key2.getSecondParam() +"...v2:null");
                context.write(key2, value);
            }
        }
    }

    public static class MyReducer extends Reducer<TwoIntWritable, NullWritable, Text, NullWritable> {
        private Text outputKey = new Text();
        @Override
        protected void reduce(TwoIntWritable k2, Iterable<NullWritable> v2s, Context context)
                throws IOException, InterruptedException {
            // System.out.println("k2: "+ k2.getFirstParam() + "  " + k2.getSecondParam());
            //  组装k3,v3
            String k3 = String.format("%d->%d", Math.min(k2.getFirstParam(), k2.getSecondParam()),
                    Math.max(k2.getFirstParam(), k2.getSecondParam()));
            System.out.println("<k3,v3>=<"+k3+">");
            //  把结果写出去
            outputKey.set(k3);
            context.write(outputKey, NullWritable.get());
        }
    }
}
