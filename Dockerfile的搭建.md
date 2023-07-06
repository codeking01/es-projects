## Docker-Compose部署注意事项

**遇到的问题：**

如果说是出现

```
Sending build context to Docker daemon  139.8kB
Step 1/4 : FROM java:8
 ---> d23bdf5b1b1b
Step 2/4 : COPY ./target/hotel-demo.jar .
1 error occurred:
        * Status: COPY failed: file not found in build context or excluded by .dockerignore: stat target/hotel-demo.jar: file does not exist, Code: 1
```

确保你的路径没问题的情况下，这个是因为扫描路径的文件过多导致的，因为的我之前的`target`下的文件过多，将这个`jar`换个位置就行了

**部署方式**

```
#1.搭建 Dockerfile
FROM java:8
#WORKDIR /usr/local
# 将当前目录下的所有 jar 包复制到容器中的 /app 目录下
COPY ./hotel-demo.jar .
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone
# 设置容器启动时执行的命令
CMD ["java", "-jar", "hotel-demo.jar"]
EXPOSE 8089
LABEL name="hotel-demo"
```

```
#2.搭建 docker-compose.yml
version: "3"
services:
  # 定义 Spring Boot 应用程序所需要的服务（容器）
  hodel-image:
    # 构建镜像的路径。"." 表示 Dockerfile 文件所在的当前目录
    build: .
    # 指定容器名称
    container_name: hotel-demo-container
    # 容器所要使用的端口号
    ports:
      - "8089:8089"
```





## 搭建sentinel容器

1.新建dockerfile

```
# java 版本
FROM java:8
# 挂载的docker卷
# VOLUME /tmp
# 前者是要操作的jar包(和dockerfile放在同一个文件下)  后者自定义jar包名
ADD sentinel-dashboard-1.7.0.jar sentinel-dashboard.jar
# 定义时区参数
ENV TZ=Asia/Shanghai
# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone
# 配置启动命令,-D表示设置JVM参数  （xxx.xxx.xxx.xxx换成自己服务器的ip）
ENTRYPOINT ["java","-jar","-Dserver.port=8858","-Dcsp.sentinel.dashboard.server=127.0.0.1:8858","-Dproject.name=sentinel-dashboard","-Dsentinel.dashboard.auth.username=sentinel","-Dsentinel.dashboard.auth.password=sentinel","/sentinel-dashboard.jar"]
```

2.去修改idea的`dockerfile`（文件）的`运行配置`(右击 修改运行配置)

2.1在镜像标记写入：sentinel-dashboard:v1.0

2.2在点击修改，然后`绑定端口` `8858:8858`

如果是其他的`springboot项目`，可以修改下面的`执行前`,然后点击`+`

    选择`maven goal`,然后在`command line`  输入  `packge`

3.右击运行`dockerfile`即可
