# Linux相关指令

## 一、linux基础命令

### linux文件目录

![图片描述](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B5f3f96ec0940fbac09420139.jpg)

- bin 存放二进制可执行文件(ls,cat,mkdir等)
- boot 存放用于系统引导时使用的各种文件
- dev 存放设备文件
- etc 存放系统配置文件
- home 存放所有用户文件的根目录
- lib 存放跟文件系统中的程序运行所需要的共享库及内核模块
- proc 虚拟文件系统，存放当前内存的映射
- usr 存放系统应用程序，比较重要的目录/usr/local 管理员软件安装目录
- var 存放运行时需要改变数据的文件
- mnt 挂载目录
- sbin 存储管理级别的二进制执行文件
- root 超级用户主目录
- opt 额外安装的可选应用程序包安装位置

### 文件查看

pwd 列出当前目录的路径

```linux
[root@localhost ~]# pwd
/root
```

ls 列出当前目录下的所有文件

```
[root@localhost ~]# ls
anaconda-ks.cfg
```

ll(ls -l缩写) 列出当前目录下的文件（带文件信息）

```
[root@localhost ~]# ll
total 4
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
```

ll -a 列出当前目录下的所有文件（包括隐藏文件）

```
[root@localhost ~]# ll -a
total 28
dr-xr-x---.  2 root root  135 Mar 28 21:00 .
dr-xr-xr-x. 17 root root  224 Mar 28 20:58 ..
-rw-------.  1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-------.  1 root root    8 Mar 28 21:00 .bash_history
-rw-r--r--.  1 root root   18 Dec 29  2013 .bash_logout
-rw-r--r--.  1 root root  176 Dec 29  2013 .bash_profile
-rw-r--r--.  1 root root  176 Dec 29  2013 .bashrc
-rw-r--r--.  1 root root  100 Dec 29  2013 .cshrc
-rw-r--r--.  1 root root  129 Dec 29  2013 .tcshrc
```

### 文件创建

touch filename 创建空文件，创建空文件hello.txt：

```
[root@localhost ~]# touch hello.txt
[root@localhost ~]# ll
total 4
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
```

mkdir 创建目录，创建目录abc

```
[root@localhost ~]# mkdir abc
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abc
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
```

mkdir -p 目标目录存在也不报错
当我们在创建目录时不确定这个目录是否已存在的时候，可以使用-p参数， 就算目录已存在也不会报错，如果不指定-p参数会报错，会提示目录已存在创建目录abc

```
[root@localhost ~]# mkdir -p abc
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abc
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
[root@localhost ~]# mkdir abc
mkdir: cannot create directory ‘abc’: File exists

```

mv 重命名文件\文件夹，修改目录abc的名称为abx

```
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abc
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
[root@localhost ~]# mv abc abx
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
```

### 链接文件

linux有两种链接：硬链接、符号(软)链接
软链接功能类似类似于windows的快捷方式，主要用于节省磁盘空间
首先看硬链接：硬链接相当于对原始文件的一个复制，不能对目录使用硬链接。

命令如下：ln hello.txt hlink

```
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
[root@localhost ~]# ln hello.txt hlink
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 2 root root    0 Mar 28 22:21 hello.txt
-rw-r--r--. 2 root root    0 Mar 28 22:21 hlink
```

如果想使用软连接，需要添加-s，相当于快捷方式，不能删除原文件
命令如下：ln -s hello.txt vlink

```
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 2 root root    0 Mar 28 22:21 hello.txt
-rw-r--r--. 2 root root    0 Mar 28 22:21 hlink
[root@localhost ~]# ln -s hello.txt  vlink
[root@localhost ~]# ll
total 4
drwxr-xr-x. 2 root root    6 Mar 28 22:22 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
-rw-r--r--. 2 root root    0 Mar 28 22:21 hello.txt
-rw-r--r--. 2 root root    0 Mar 28 22:21 hlink
lrwxrwxrwx. 1 root root    9 Mar 28 22:33 vlink -> hello.txt
```

### 切换目录

cd . 当前目录

```
[root@localhost ~]# pwd
/root
[root@localhost ~]# cd .
[root@localhost ~]# pwd
/root
```

cd … 去上一级目录， 两个…表示上一级目录

```
[root@localhost ~]# pwd
/root
[root@localhost ~]# cd ..
[root@localhost /]# pwd
/
```

cd / 去根目录

```
[root@localhost /]# cd /
[root@localhost /]# pwd
/
[root@localhost ~]# cd /bin/
[root@localhost bin]# pwd
/bin
```

cd xxx/xxx 直接跳转到某个目录

```
[root@localhost ~]# cd abx/test/
[root@localhost test]# pwd
/root/abx/test
```

### 删除文件\文件夹(目录)

rm命令可以删除文件或者目录，也可以将某个目录及其下属的所有文件及其子目录均删除掉
对于链接文件，只是删除整个链接文件，而原有文件保持不变。
常见用法：
rm 删除文件
删除文件，但是会有提示确认对话，输入y确认删除！

```
[root@localhost test]# touch abc.txt
[root@localhost test]# ll
total 0
-rw-r--r--. 1 root root 0 Mar 29 13:53 abc.txt
[root@localhost test]# rm abc.txt
rm: remove regular empty file ‘abc.txt’? y
[root@localhost test]# ll
total 0

```

rm -r 删除目录，需要确认
删除目录需要指定r参数，否则会提示不能删除
r是给rm加入递归(recursion)特性，也就是目标为文件夹时删除文件夹下所有数据
使用rm -r 在删除目录的时候也会有提示确认对话，输入y确认删除

```
[root@localhost abx]# ll
total 0
drwxr-xr-x. 2 root root 6 Mar 29 14:01 test
[root@localhost abx]# rm test
rm: cannot remove ‘test’: Is a directory
[root@localhost abx]# rm -r test
rm: remove directory ‘test’? y
[root@localhost abx]# ll
total 0
```

rm -f 强制删除
f给rm加入强制(force)特性，也就是遇到删除时不需要询问即可直接删除
注意：这个操作还是比较危险的，建议慎用，因为删除之后就找不到了
Linux系统中没有回收站

```
[root@localhost abx]# touch a.txt
[root@localhost abx]# ll
total 0
-rw-r--r--. 1 root root 0 Mar 29 14:03 a.txt
[root@localhost abx]# rm -f a.txt 
[root@localhost abx]# ll
total 0
```

rm -rf 递归删除目录及其文件
**`Linux中最危险的操作，最具破坏性`**
**`rf参数可以强制递归删除任何数据，并且没有任何提示，慎用！慎用！慎用！`**

```
[root@localhost ~]# ll
drwxr-xr-x. 2 root root    6 Mar 29 14:03 abx
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
[root@localhost ~]# mkdir -p abx/test/aaa
[root@localhost ~]# cd abx/test/aaa/
[root@localhost aaa]# touch a.txt
[root@localhost aaa]# cd ~
[root@localhost ~]# rm -rf abx
[root@localhost ~]# ll
-rw-------. 1 root root 1243 Mar 28 20:59 anaconda-ks.cfg
```

### 复制\粘贴\剪切

常见用法：
cp 复制&粘贴文件
复制hello.txt文件，复制后的文件名为hello-bak.txt

```
[root@localhost ~]# ll
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
[root@localhost ~]# cp hello.txt  hello-bak.txt  
[root@localhost ~]# ll
-rw-r--r--. 1 root root    0 Mar 29 14:20 hello-bak.txt
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
```

cp -r 复制&粘贴文件或目录
复制目录，需要指定r参数

```
[root@localhost ~]# mkdir abc
[root@localhost ~]# ll
drwxr-xr-x. 2 root root    6 Mar 29 14:21 abc
[root@localhost ~]# cp abc xyz 【错误用法，复制目录必须指定-r参数】
cp: omitting directory ‘abc’
[root@localhost ~]# cp -r abc xyz
[root@localhost ~]# ll
drwxr-xr-x. 2 root root    6 Mar 29 14:21 abc
drwxr-xr-x. 2 root root    6 Mar 29 14:21 xyz
```

mv 移动（剪切）文件或目录
将目录xyz移动到目录abc下面

```
[root@localhost ~]# ll
drwxr-xr-x. 2 root root    6 Mar 29 14:21 abc
drwxr-xr-x. 2 root root    6 Mar 29 14:21 xyz
[root@localhost ~]# ll abc/
total 0
[root@localhost ~]# mv xyz abc 
[root@localhost ~]# ll abc/
drwxr-xr-x. 2 root root 6 Mar 29 14:21 xyz
```

### 远程复制

scp命令用于在网络中不同主机之间复制文件或目录。scp是有Security的文件copy，基于ssh登录，如果没有配置免密码登陆，需要输入主机密码。
常见用法：
从本地复制（192.168.182.131）到远程主机 （192.168.182.130）
scp /root/hello.txt 192.168.182.130:/root

```
[root@localhost ~]# scp /root/hello.txt  192.168.182.130:/root/
The authenticity of host '192.168.182.130 (192.168.182.130)' can't be established.
ECDSA key fingerprint is SHA256:uUG2QrWRlzXcwfv6GUot9DVs9c+iFugZ7FhR89m2S00.
ECDSA key fingerprint is MD5:82:9d:01:51:06:a7:14:24:a9:16:3d:a1:5e:6d:0d:16.
Are you sure you want to continue connecting (yes/no)? yes 【第一次会提示此信息，输入yes即可，以后就不提示了】
Warning: Permanently added '192.168.182.130' (ECDSA) to the list of known hosts.
root@192.168.182.130's password: 【在这里输入192.168.182.130机器的密码即可】
hello.txt                        100%    0     0.0KB/s   00:00
```

提示：
显示进度在scp后添加-v
复制目录在scp后添加-r
静默复制模式在scp后添加-q
scp -rq /root/abc 192.168.182.130:/root

```
[root@localhost ~]# scp -rq /root/abc/ 192.168.182.130:/root/
root@192.168.182.130's password:【在这里输入192.168.182.130机器的密码即可】
```

### 文件属性

![图片描述](https://damon-study.oss-cn-shenzhen.aliyuncs.com/%20typora/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B5f3f971209af386c09530263.jpg)

黑带七段
第一段：权限
第一个字符代表文件（-）、目录（d），链接（l）
其余字符每3个一组（rwx），读（r）、写（w）、执行（x）
第一组：文件所有者的权限是读、写和执行
第二组：与文件所有者同一组的用户的权限
第三组：不与文件所有者同组的其他用户的权限
也可用数字表示为：r=4，w=2，x=1，如：权限6可以表示为r+w=6
第二段：目录/链接个数
对于目录文件，表示它的第一级子目录的个数
注意：此处的值要减2才等于该目录下的子目录的实际个数(目录下默认包含.和…这两个目录)
对于其他文件，默认是1
第三段：所属用户
第四段：所属组
第五段：文件大小（字节）
第六段：最后修改时间
第七段：文件\文件夹名称

### chmod 分配权限

常见用法：
chmod u+x xxx.txt 给当前所有者添加执行权限【x表示是执行权限】
针对hello.txt文件，给当前用户添加执行权限

```
[root@localhost ~]# ll
-rw-r--r--. 1 root root    0 Mar 28 22:21 hello.txt
[root@localhost ~]# chmod u+x hello.txt 
[root@localhost ~]# ll
-rwxr--r--. 1 root root    0 Mar 28 22:21 hello.txt
```

chmod 777 xxx.txt 添加rwxrwxrwx权限
给hello.txt添加777权限

```
[root@localhost ~]# chmod 777 hello.txt
[root@localhost ~]# ll
-rwxrwxrwx. 1 root root    0 Mar 28 22:21 hello.txt
```

chmod -R 777 xxx 给指定目录递归添加rwxrwxrwx权限
给abc目录及其子目录中的所有内容添加777权限

```
[root@localhost ~]# ll
drwxr-xr-x. 3 root root   17 Mar 29 14:24 abc
[root@localhost ~]# cd abc/
[root@localhost abc]# ll
total 0
drwxr-xr-x. 2 root root 6 Mar 29 14:21 xyz
[root@localhost abc]# cd ..
[root@localhost ~]# chmod -R 777 abc
[root@localhost ~]# ll
drwxrwxrwx. 3 root root   17 Mar 29 14:24 abc
[root@localhost ~]# cd abc/
[root@localhost abc]# ll
total 0
drwxrwxrwx. 2 root root 6 Mar 29 14:21 xyz
```

### 内容查看

cat 显示文本内容，类似windows中的type(顺序输出)

```
[root@localhost ~]# cat anaconda-ks.cfg 
#version=DEVEL
#System authorization information
auth --enableshadow --passalgo=sha512
......
```

cat -b 显示行号输出

```
[root@localhost ~]# cat -b anaconda-ks.cfg 
     1  #version=DEVEL
     2  # System authorization information
     3  auth --enableshadow --passalgo=sha512
     4  # Use CDROM installation media
........
```

分屏显示 more
用一次显示一屏，没有显示完时最后一行显示进度。回车显示下一行，按b显示上一页，空格显示下一页，q退出。

根据当前屏幕大小显示一屏内容

```
[root@localhost ~]# more anaconda-ks.cfg 
#version=DEVEL
#System authorization information
auth --enableshadow --passalgo=sha512
#Use CDROM installation media
cdrom
#Use graphical install
graphical
#Run the Setup Agent on first boot
firstboot --enable
ignoredisk --only-use=sda
#Keyboard layouts
keyboard --vckeymap=us --xlayouts='us'
#System language
lang en_US.UTF-8

#Network information
network  --bootproto=dhcp --device=ens33 --ipv6=auto --activate
network  --hostname=localhost.localdomain

#Root password
rootpw --iscrypted $6$sx2fOWF8AMiHgQTV$ExkpEX6Sq1EfZVHaP4RxfYls3B0o
dX2ouFfaTX2S0TttzWz7tX3L3cWRFeb1M4qfGUA2FGzrkylhlGfp4psze.
--More--(48%)
```

### 压缩、解压

常见用法：压缩和解压
参数：
-z 是否同时具有 gzip 的属性？亦即是否需要用 gzip 压缩？
-c 创建一个压缩文件的参数指令(create 的意思)；
-x 解开一个压缩文件的参数指令！
-v 压缩的过程中显示文件！
-f 使用档案名字，这个参数是最后一个参数，后面只能接档案名！
注意：特别注意，在参数的下达中， c/x 仅能存在一个！不可同时存在！

压缩：
tar -zcvf 打包及压缩（gzip方式）
将abc目录的内容打包压缩为abc.tar.gz

```
[root@localhost ~]# ll
drwxrwxrwx. 3 root root   17 Mar 29 14:24 abc
[root@localhost ~]# tar -zcvf abc.tar.gz abc
abc/
abc/xyz/
[root@localhost ~]# ll
drwxrwxrwx. 3 root root   17 Mar 29 14:24 abc
-rw-r--r--. 1 root root  130 Mar 29 15:24 abc.tar.gz
```

解压：
tar -zxvf 解压（gzip包）
先把abc.tar.gz移动到test目录下，再解压缩

```
[root@localhost ~]# ll
drwxrwxrwx. 3 root root   17 Mar 29 14:24 abc
-rw-r--r--. 1 root root  130 Mar 29 15:24 abc.tar.gz
[root@localhost ~]# mkdir test
[root@localhost ~]# cd test/
[root@localhost test]# mv ../abc.tar.gz  .
[root@localhost test]# ll
total 4
-rw-r--r--. 1 root root 130 Mar 29 15:24 abc.tar.gz
[root@localhost test]# tar -zxvf abc.tar.gz 
abc/
abc/xyz/
[root@localhost test]# ll
total 4
drwxrwxrwx. 3 root root  17 Mar 29 14:24 abc
-rw-r--r--. 1 root root 130 Mar 29 15:24 abc.tar.gz
```

### 输出及显示

echo：将内容输出到设备，类似java里面的system.out.println()
常见用法：
echo “hello\t\t world！” 不解析转义字符
echo -e “hello\t\t world！” 解析转义字符
echo $PATH 输出环境变量
注意：在打印变量信息的时候，使用echo ${PATH} 也可以，效果是一样的

```
[root@localhost ~]# echo "hello\t\t world！"
hello\t\t world！
[root@localhost ~]# echo -e "hello\t\t world！"
hello            world！
[root@localhost ~]# echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
[root@localhost ~]# echo ${PATH}
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
```

### 软件安装和卸载

第一种：压缩包安装方式，直接解压，配置相应的环境变量即可使用
第二种：在线安装，使用yum
yum集成了连接网络，软件安装，删除，更新等功能，yum在配置好repo后，机器只要连网，就能智能化安装软件，使用yum 安装的好处在于可以自动安装软件需要的依赖包
安装
yum install -y 安装
升级
yum update 不跟则更新全部
查找和显示
yum info 显示包信息
yum list 不跟则显示已安装或可安装包
删除程序
yum remove
清除缓存
yum clean all 清除所有缓存（包含文件、旧软件）

### 查看操作历史

history保留了最近执行的命令记录，默认可以保留1000。
历史清单从0开始编号到最大值。
常见用法：
history N 显示最近N条命令
history -c 清除所有的历史记录
history -w xxx.txt 保存历史记录到文本xxx.txt

```
[root@localhost ~]# history 10
  142  ll
  143  tar -zxvf abc.tar.gz 
  144  ll
  145  cd .
  146  ll
  147  echo "hello\t\t world！"
  148  echo -e "hello\t\t world！"
  149  env
  150  echo $PATH
  151  history 10
[root@localhost ~]# history  -w his.txt
[root@localhost ~]# ll
-rw-------. 1 root root 1395 Mar 29 15:41 his.txt
[root@localhost ~]# more his.txt 
ip addr
ls /
pwd
ls
ll
..........
```

### 磁盘使用情况

使用df命令查看硬盘使用情况
查看磁盘使用情况
这里显示一共17G，已使用1.2G，还剩余16G

```
[root@localhost ~]# df -h
Filesystem               Size  Used Avail Use% Mounted on
devtmpfs                 898M     0  898M   0% /dev
tmpfs                    910M     0  910M   0% /dev/shm
tmpfs                    910M  9.5M  901M   2% /run
tmpfs                    910M     0  910M   0% /sys/fs/cgroup
/dev/mapper/centos-root   17G  1.2G   16G   7% /
/dev/sda1               1014M  150M  865M  15% /boot
tmpfs                    182M     0  182M   0% /run/user/0
```

### 清屏小命令

在控制台操作一会以后，屏幕上就显示了一屏内容，看起来乱成一团，可以使用clear命令清屏，类似于widows中的cls命令。

### 查看内存使用情况

free 查看内存和交换空间的使用情况
常见用法：
free -m：显示内存单位为MB
free -h：根据值的大小，显示易于识别的单位

```
[root@localhost ~]# free -m
              total        used        free      shared  buff/cache   available
Mem:           1819         169        1509           9         140        1501
Swap:          2047           0        2047
[root@localhost ~]# free -h
              total        used        free      shared  buff/cache   available
Mem:           1.8G        169M        1.5G        9.5M        140M        1.5G
Swap:          2.0G          0B        2.0G
```

### 关机重启快捷命令

shutdown -h now 关机
reboot -h now 重启
exit 退出当前登录状态

## 二、高级指令

### vi文件编辑

vi hello.txt
表示要编辑hello.txt文件，不管这个文件存在与否都可以，如果不存在，最后在保存的时候会创建这个文件。
此时我们处于一个不可编辑的模式，如果想编辑文件，需要按键盘上的i键，进入编辑模式。
然后在文件中输入内容：hello world!
文件编辑好了以后我们想要保存，此时需要按键盘左上角的esc键，退出编辑模式，此时即进入了命令模式（命令模式其实就是我们最开始进入的那个不可编辑的模式）
在命令模式下按shift和: ，最后输入wq
这里的w是write的缩写，表示写入的意思，q是quit的缩写，表示退出的意思

**查找字符串**：如果我们想要在一个大文件中查找某一个字符串进行修改，按照我们现在学习的知识，通过vi命令打开文件，然后按键盘上的上下键来滚动光标一行一行肉眼扫描，这样如果碰到上万行的文件，你想哭都不知道该怎么哭了。
所以在这就教给大家一个解脱的方法，在命令模式下，输入/，然后再输入你想要查询的字符串，最后按回车键就可以进行查询了。

在这里我们查询linux系统中自带的文件anaconda-ks.cfg

vi anaconda-ks.cfg 【此时就进入了命令模式，如果是在编辑模式想进入命令模式，只需要按键盘左上角的esc键即可】

然后输入/
最后输入我们想要查询的字符串，root，按回车键即可进行查找
这个文件中其实有多个root字符串，如果第一次查找到的不是我们想要的，可以按n这个键继续向下查找。n表示next的意思，获取下一个匹配的字符串。查找到以后就可以按i 进入编辑模式修改，修改好了以后直接保存退出即可。
这是对文件中字符串的快速查找。

**查找某一行内容**：如果我们已经知道了需要修改的内容在文件的第几行，能不能直接定位到那一行呢？当然是可以的。

先按shift和: 然后输入 set nu 这个时候就可以看到文件中显示了行号，shift和: 然后输入10 就会发现光标跳转到第10行了

**复制粘贴**：如果我们需要在文件中根据某一行内容快速复制几行，不用麻烦鼠标了，直接通过键盘操作就行，有研究表明，用键盘操作的效率比鼠标快10倍
使用vi命令打开hello.txt，把光标移动到希望复制的那一行内容上面，然后连按yy，这样就把这一行内容复制上了，然后按p就会把刚才复制的内容粘贴到下一行，按一次p粘贴一行，一直按到你喊停为止。
最后按shift和: 输入wq保存退出即可。

**快速删除**：如果我们想删除文件中的某几行内容，默认可以进入编辑模式使用退格键删除，按一次删一个字符，这样按的时间长了手指头肯定抽筋啊，所以我们选择更加快捷的方式。
进入命令模式，把光标定位到想要删除的那一行内容上面，连按dd，就可以删除当前行的内容。
还有一个大招，如果想要清空当前行下的所有内容，先连按999，然后再连按dd，这样就可以清空光标所在行下的所有内容了。
那如果想要清空当前文件所有内容呢？你懂得！

**快速跳到文件首行和末行**：在工作中有这种场景，一个配置文件有几千行内容，我们知道要修改的内容大致在最后几行，但是具体的行号和关键字都记不清楚了，这个时候难道就只能通过键盘的下箭头一行一行的来挪动光标吗？那还不崩溃了
所以大家要记好下面这个命令了，它可以解救你与水火之中，在命令模式下，通过大写的G可以快速将光标移动到最后一行。

当然了这个时候如果还要再回退到第一行，也很简单，在命令模式下输入小写的gg即可快速跳转到第一行。

### 文件内容统计命令

- wc：统计字数相关信息

-c是表示获取文件内容的字节数量
-m表示获取字符数量
-l表示是行数
-L是获取最长的一行内容的长度
-w 表示文件中单词的个数，默认使用空白符切割

```
[root@localhost ~]# wc -c hello.txt  
78 hello.txt
[root@localhost ~]# wc -m hello.txt 
78 hello.txt
[root@localhost ~]# wc -l hello.txt  
6 hello.txt
[root@localhost ~]# wc -L hello.txt  
12 hello.txt
[root@localhost ~]# wc -w hello.txt  
12 hello.txt
```

- sort：排序

sort命令是对数据进行排序的，它后面也支持很多个参数，我们在这里只讲三个
首先我们创建一个新文件，因为涉及到排序，我们还是创建一个带数字的文件

-n 正序
-nr 倒序

```
[root@localhost ~]# sort -n num.txt 
1
2
3
9
10

[root@localhost ~]# sort -nr num.txt 
10
9
3
2
1

```

在-n的基础上增加-k 这个参数后面需要指定一个数字，这个数字表示是文件中的第几列，编号从1开始

```
[root@localhost ~]# sort -k 2 -n num2.txt   
ax 1
bc 2
dd 7
aa 9
xc 15
```

- uniq：检查重复的行列

看一下uniq的帮助文档，这里面命令也不少，我就不挨个演示了，挑几个比较常见的看一下。
先看一下什么参数都不带的

```
[root@localhost ~]# uniq  hello.txt
hello world!
```

再看一下-c参数，这个参数表示在输出行的前面加上数据在文件中重复出现的次数

```
[root@localhost ~]# uniq -c hello.txt 
      6 hello world!
```

还有一个-u参数，表示返回文件中不重复的行，针对hello.txt这个文件返回的是空，因为这个文件中的几行内容都是重复的。

```
[root@localhost ~]# cat hello.txt 
hello world!
hello world!
hello world!
hello world!
hello world!
hello world!
abc
[root@localhost ~]# uniq -u hello.txt  
abc
```

- head：取前N条数据

最后来看一下head命令，head表示获取前N条数据，默认返回前10条，后面可以通过指定数字来控制返回的数据条数

```
[root@localhost ~]# cat num.txt 
3
2
9
10
1
[root@localhost ~]# head -3 num.txt 
3
2
9
```

这样是没有什么意义的，我们想取前几条数据其实就是想取topN，这样直接获取的数据是没有排序的，所以可以把sort和head命令放在一块使用

```
[root@localhost ~]# sort -nr num.txt 
10
9
3
2
1
[root@localhost ~]# sort -nr num.txt  | head -3
10
9
3
```

其实我们前面学习的这些命令都可以处理管道传输过来的数据。
例如 cat和sort命令

```
[root@localhost ~]# cat num.txt  | sort -nr
10
9
3
2
1
```

### 日期相关

date命令默认获取系统当前时间

```
[root@localhost ~]# date
Sun Mar 29 20:48:15 CST 2026
```

date +"%Y-%m-%d %H:%M:%S"
注意：date后面的这些参数中间如果没有空格，可以省略双引号。

```
[root@localhost ~]# date +"%Y-%m-%d %H:%M:%S"
2026-03-29 21:06:45
```

date +%s

```
[root@localhost ~]# date +%s
1585487600
```

如果想要获取毫秒数呢？不好意思，date命令不支持
注意了，虽然date命令没有直接支持获取毫秒数，但是从秒换算为毫秒也很简单啊，最直接粗暴的方式就是在秒数后面加3个0
date +%s"000"

```
[root@localhost ~]# date +%s"000"
1585487796000
```

获取昨天的日期，这个需求也需要使用–date参数实现
date --date=“1 days ago”

```
[root@localhost ~]# date --date="1 days ago"
Sat Mar 28 21:36:37 CST 2026
```

再对返回的结果进行格式化，只获取年月日
date --date=“1 days ago” +%Y-%m-%d

```
[root@localhost ~]# date --date="1 days ago" +%Y-%m-%d
2026-03-28
```

