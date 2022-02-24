#!/bin/bash
#后台启动项目脚本
echo "publish-------------"

process_id=`ps -ef | grep wiki.jar | grep -v grep |awk '{print $2}'`
if [ $process_id ]; then
    sudo kill -9 $process_id
fi
#加载环境变量
source /etc/profile
nohup java -jar -Dspring.profiles.active=prod ~/wiki/wiki.jar > /dev/null 2>&1 &

echo "end publish"
