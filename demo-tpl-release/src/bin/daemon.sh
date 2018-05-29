#!/bin/bash
START_HOME=$(cd "$(dirname "$0")"; pwd)/../
START_HOME=$(readlink -f $START_HOME)
echo "demo-tpl shell dir is :$START_HOME"
cd $START_HOME/bin/
/bin/bash ./demo-tpl stop -f
sleep 3

#kill -9
pidStart=$(ps aux | grep 'demo-tpl' | grep 'java' | grep -v grep | awk '{print $2}')
echo "pidStart:$pidStart"
if [ "$pidStart"X = ""X ]; then
    echo "direct start:"
    /bin/bash ./demo-tpl.sh start  -d -ao false -a '--server.port=9991'
else
    echo "must be stop first:"
    killResult=$(kill -9 $pidStart)
    sleep 3
    /bin/bash ./demo-tpl.sh start  -d -ao false -a '--server.port=9991'
fi
