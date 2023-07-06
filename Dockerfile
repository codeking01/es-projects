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
