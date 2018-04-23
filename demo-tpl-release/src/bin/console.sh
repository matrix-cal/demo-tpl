#!/bin/bash
START_HOME=$(cd "$(dirname "$0")"; pwd)/../
START_HOME=$(readlink -f $START_HOME)
echo "demo-tpl shell dir is :$START_HOME"
cd $START_HOME/bin/


echo "direct start:"
/bin/bash ./demo-tpl.sh start -ssid  -ao false -a '--server.port=9991' -jo '-Docean.log.mode=stdout'

