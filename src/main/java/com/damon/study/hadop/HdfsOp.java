package com.damon.study.hadop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HdfsOp {

    public static void main(String[] args) throws IOException {
        //创建一个配置对象
        Configuration conf = new Configuration();
        //指定HDFS的地址
        conf.set("fs.defaultFS","hdfs://bigdata01:9000");
        ///获取操作HDFS的对象
        FileSystem fileSystem = FileSystem.get(conf);

        //文件上传
        // put(fileSystem);

        //下载文件
        // get(fileSystem);

        delete(fileSystem);
    }

    /**
     * 文件上传
     *
     * @param fileSystem
     * @throws IOException
     */
    private static void put(FileSystem fileSystem) throws IOException {
        //获取HDFS文件系统的输出流
        FSDataOutputStream fos = fileSystem.create(new Path("/user.txt"));
        //获取本地文件的输入流
        FileInputStream fis = new FileInputStream("D:\\user.txt");
        //上传文件：通过工具类把输入流拷贝到输出流里面，实现本地文件上传到HDFS
        IOUtils.copyBytes(fis,fos,1024,true);
    }

    /**
     * 下载文件
     *
     * @param fileSystem
     * @throws IOException
     */
    private static void get(FileSystem fileSystem) throws IOException {
        //获取HDFS文件系统的输入流
        FSDataInputStream fis = fileSystem.open(new Path("/README.txt"));
        //获取本地文件的输出流
        FileOutputStream fos = new FileOutputStream("D:\\README.txt");
        //下载文件
        IOUtils.copyBytes(fis,fos,1024,true);
    }

    /**
     * 下载文件
     *
     * @param fileSystem
     * @throws IOException
     */
    private static void delete(FileSystem fileSystem) throws IOException {
        //获取HDFS文件系统的输入流
        boolean flag =  fileSystem.delete(new Path("/LICENSE.txt"), true);
        if(flag){
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }
    }
}
