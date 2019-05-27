#!/bin/bash
APP_KEY_NAME=tiano-pub
START_HOME=$(cd "$(dirname "$0")"; pwd)/../
START_HOME=$(readlink -f $START_HOME)
echo "${APP_KEY_NAME} shell dir is :$START_HOME"
cd $START_HOME/bin/
/bin/bash ./${APP_KEY_NAME} stop -f
sleep 1

#kill -9
pidStart=$(ps aux | grep ${APP_KEY_NAME} | grep 'java' | grep -v grep | awk '{print $2}')
echo "pidStart:$pidStart"
if [ "$pidStart"X = ""X ]; then
    echo "direct start:"
    /bin/bash ./${APP_KEY_NAME} start -d -ssid
else
    echo "must be stop first:"
    killResult=$(kill -9 $pidStart)
    sleep 3
    /bin/bash ./${APP_KEY_NAME} start -d -ssid
fi
