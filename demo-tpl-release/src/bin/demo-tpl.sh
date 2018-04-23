#!/bin/bash
THIS_FILE_PATH=$(readlink -f $0)
THIS_FILE_DIR=$(dirname "$THIS_FILE_PATH")

SAVE_DETONATOR_FILE=false
DETONATOR_LOG_FILE="${THIS_FILE_DIR}/detonator_log_$(echo $$).log"

DETONATOR_INFO="Detonator Version: 1.5.0, Released on 2017-12-18 13:17:08"
COMPONENT_NAME_GET_URL="http://component-manager.ocean.douyu.com/component/manager/version/name?appName="
MANAGER_REQUEST_URL_TEMPLATE="http://component-manager.ocean.douyu.com/component/manager/version?appName="

log_generic (){
    if [[ "${SAVE_DETONATOR_FILE}" == "true" ]]; then
        echo "$(date '+%Y-%m-%dT%H:%M:%S.%3N%z') | $1 | detonator | $2" | tee -a "${DETONATOR_LOG_FILE}"
    else
        echo "$(date '+%Y-%m-%dT%H:%M:%S.%3N%z') | $1 | detonator | $2"
    fi
}

log_info(){
    log_generic "INFO " "$1"
}

log_warn(){
    log_generic "WARN " "$1"
}

log_error(){
    log_generic "ERROR" "$1"
}

parse_jvm_options() {
  if [ -f "$1" ]; then
    echo "$(grep "^-" "$1" | tr '\n' ' ')"
  fi
}

get_property_in_file() {
  if [ -f "$2" ]; then
    echo "$(grep "^$1=" "$2" | tail -1 | cut -d "=" -f 2 )"
  fi
}

test_dir_available() {
  local test_available_tmp_file=$1/tmp.available.$(date +%N)
  touch ${test_available_tmp_file}
  if [ -f ${test_available_tmp_file} ]; then
    rm ${test_available_tmp_file}
    echo "true"
  fi
}

main()
{
    COMMAND=$1
    case $1 in
    "start" )
        shift
        while [[ $# -gt 0 ]]
        do
            case $1 in
                "-d" | "--daemon" )
                    APP_START_IN_DAEMON=true
                    ;;
                "-c" | "--conf-dir" )
                    CONF_DIR_PARAM=$2
                    shift
                    ;;
                "--enable-log-dir-election" )
                    ENABLE_LOG_DIR_ELECTION_PARAM=true
                    ;;
                "--candidate-log-dirs" )
                    CANDIDATE_LOG_DIRS_PARAM=$2
                    shift
                    ;;
                "--log-dir-config-file" )
                    LOG_DIR_CONFIG_FILE=$2
                    shift
                    ;;
                "--auto-update-overwatch" )
                    AUTO_UPDATE_OVERWATCH="$2"
                    shift
                    ;;
                "-ao" | "--attach-overwatch" )
                    ATTACH_OVERWATCH_AGENT="$2"
                    shift
                    ;;
                "-cp" | "--classpath" )
                    CLASSPATH_PARAM="$2"
                    shift
                    ;;
                "-sn" | "--service-name")
                    SERVICE_NAME_PARAM="$2"
                    shift
                    ;;
                "-mc" | "--main-class")
                    MAIN_CLASS_PARAM="$2"
                    shift
                    ;;
                "-a" | "--args")
                    APP_ARGS_PARAM="$2"
                    shift
                    ;;
                "-sid" | "--service-id")
                    SERVICE_ID_PARAM="$2"
                    shift
                    ;;
                "-ssid" |"--static-service-id")
                    STATIC_SERVICE_ID_PARAM=true
                    ;;
                "-jo" | "--java-opts")
                    JAVA_OPTS_PARAM="${JAVA_OPTS_PARAM} $2"
                    shift
                    ;;
                "--dry-run")
                    DRY_RUN=true
                    ;;
                "-h" | "--help" )
                    echo "Usage:  $0 start [OPTIONS]"
                    echo ""
                    echo "Example:  $0 start -d -ao -sn MyService --args \"arg1 arg2 arg3\""
                    echo ""
                    echo "Start a service"
                    echo ""
                    echo "Options:"
                    echo "    -a,    --args                     Set application args"
                    echo "    -ao,   --attach-overwatch         Attach 'overwatch' java agent"
                    echo "           --auto-update-overwatch    Auto update 'overwatch' java agent"
                    echo "    -c,    --conf-dir                 Set config file dir"
                    echo "    -cp,   --classpath                Set classpath"
                    echo "    -d,    --daemon                   Run the service in background"
                    echo "           --enable-log-dir-election  Enable dynamic log dir, default enabled in docker container"
                    echo "           --candidate-log-dirs       Candidate log dirs, default '/mnt/sd*/ocean-logs'"
                    echo "           --log-dir-config-file      Log dir config file. Skip the dir if the config file not exists. default 'log-dir.config'"
                    echo "    -mc,   --main-class               Set main class to start"
                    echo "    -sn,   --service-name             Set service name"
                    echo "    -sid,  --service-id               Set service id"
                    echo "    -ssid, --static-service-id       Use static service id, will never be changed"
                    echo "    -jo,   --java-opts                Set java opts"
                    echo "           --dry-run                  Just print, not start"
                    echo "    -h,    --help                     Print usage"
                    echo ""
                    exit 0
                    ;;
                * )
                    echo "Unknow option $1"
                    echo "See '$0 start --help'"
                    echo ""
                    exit 0
            esac
            shift
        done
        log_info "${DETONATOR_INFO}"
        detonator::start
        ;;
    "stop" )
        shift
        while [[ $# -gt 0 ]]
        do
            case $1 in
                "-f" | "--force")
                     APP_FORCE_KILL=true
                     ;;
                "-sn" | "--service-name")
                     SERVICE_NAME_PARAM="$2"
                     shift
                     ;;
                * )
                     echo "Usage:  $0 stop [OPTIONS]"
                     echo ""
                     echo "Stop a service"
                     echo ""
                     echo "Options:"
                     echo "    -f,  --forcekill             Stop the Service use 'kill -9'"
                     echo "    -sn, --service-name          (Required)Set service name"
                     echo ""
                     exit 0
                     ;;
            esac
            shift
        done
        detonator::stop
        ;;
    "restart" )
        shift
        detonator::restart
        ;;
    "status" )
        shift
        while [[ $# -gt 0 ]]
        do
            case $1 in
                "-sn" | "--service-name")
                     SERVICE_NAME_PARAM="$2"
                     shift
                     ;;
                "-ch" | "--check-healthy")
                     CHECK_HEALTHY_PARAM=true
                     ;;
                "-cr" | "--check-ready")
                     CHECK_READY_PARAM=true
                     ;;
                * )
                     echo "Usage:  $0 status [OPTIONS]"
                     echo ""
                     echo "Check service status a service"
                     echo ""
                     echo "Options:"
                     echo "    -sn, --service-name          Set service name"
                     echo "    -ch, --check-healthy         Exit with error code 1 when check healthy failed"
                     echo "    -cr, --check-ready           Exit with error code 1 when check ready failed"
                     echo ""
                     exit 0
                     ;;
            esac
            shift
        done
        detonator::status
        ;;
    "shortcut" )
        shift
        while [[ $# -gt 0 ]]
        do
            case $1 in
                "-c" | "--command")
                     SHORTCUT_COMMAND="$2"
                     shift
                     ;;
                "-n" | "--name")
                     SHORTCUT_NAME="$2"
                     shift
                     ;;
                * )
                     echo "Usage:  $0 shortcut [OPTIONS]"
                     echo ""
                     echo "Create commands shortcut"
                     echo ""
                     echo "Options:"
                     echo "    -c, --command       (Required)The shortcut command, eg:'start -d -cp \${APP_HOME}/lib:\${APP_HOME}/lib/*'"
                     echo "    -n, --name          (Required)The shortcut file name, will be placed in \${APP_HOME}/bin"
                     echo ""
                     echo "Examples:"
                     echo "    ${THIS_FILE_PATH} shortcut -n shortcut-name -c \"start -a '--spring.config.location=\\\${APP_HOME}/conf/application.properties'\""
                     echo ""
                     exit 0
                     ;;
            esac
            shift
        done
        detonator::shortcut
        ;;
    "-h" | "--help" )
        echo "Usage:  $0 COMMAND [arg...]"
        echo ""
        echo "Commands:"
        echo "    start     Start a service"
        echo "    stop      Stop a service"
        echo "    restart   Restart a service"
        echo "    status    Show service status"
        echo "    shortcut  Create command shortcut"
        echo ""
        exit 0
        ;;
    * )
        echo "Unknow command $1"
        echo "See '$0 --help'"
        echo ""
        exit 0
    esac
}



detonator::check_java_home() {
    if [ ! -d "${JAVA_HOME}" ]; then
      log_error 'JAVA_HOME is not exists, please install JDK or set var "JAVA_HOME" in front of this file'
      exit 1
    fi
    JAVA_HOME="${JAVA_HOME}"
}

detonator::init_app_home() {
    APP_HOME=$(readlink -f $(dirname ${THIS_FILE_PATH})/../)

    if [ -z "${APP_HOME}" ]; then
        log_error "APP home init failed"
        exit 1
    fi

    if [ ! -w "${APP_HOME}" ]; then
        log_error "APP home \"${APP_HOME}\" has no writable permission"
        exit 1
    fi

    log_info "Service home dir is: \"${APP_HOME}\""
}

detonator::init_conf_dir() {

    APP_CONF_DIR="${APP_HOME}/conf"

    if [ ! -z "${CONF_DIR_PARAM}" ]; then

        local absolute_conf_dir="${CONF_DIR_PARAM}"

        if [ "${absolute_conf_dir:0:1}" != '/' ]; then
            absolute_conf_dir="${APP_HOME}/${CONF_DIR_PARAM}"
            log_info "Conf dir in param \"${CONF_DIR_PARAM}\" not absolutely, replace to \"${absolute_conf_dir}\""
        fi

        if [ -d "${absolute_conf_dir}" ]; then
            APP_CONF_DIR=$(readlink -f ${absolute_conf_dir})
        else
            log_error "Conf dir \"${absolute_conf_dir}\" not found"
            exit 1
        fi
    fi

    log_info "Set conf dir: ${APP_CONF_DIR}"
}

detonator::init_main_class() {
    # 使用参数中的SERVICE_NAME
    if [ "${MAIN_CLASS_PARAM}"x != ""x ]; then
        APP_MAIN_CLASS="${MAIN_CLASS_PARAM}"
        log_info "Set MAIN_CLASS from param  \"${MAIN_CLASS_PARAM}\""
        return
    fi

    # 使用配置文件中的SERVICE_NAME
    if [ -f "${APP_CONF_DIR}/service.properties" ];then
        APP_MAIN_CLASS="$(get_property_in_file "MAIN_CLASS" "${APP_CONF_DIR}/service.properties" )"
        eval APP_MAIN_CLASS=${APP_MAIN_CLASS}

        if [ -z "${APP_MAIN_CLASS}" ]; then
            log_warn "MAIN_CLASS not found in service.properties"
        else
            log_info "Set MAIN_CLASS from service.properties \"${APP_MAIN_CLASS}\""
        fi
    else
        log_warn "Service config '${APP_CONF_DIR}/service.properties' not exists."
    fi

    if [ -z "${APP_MAIN_CLASS}" ] && [ "start"x = "${COMMAND}"x ]; then
        log_error "Service MAIN_CLASS init failed"
        exit 1
    fi
}

detonator::init_service_name() {

    # 使用参数中的SERVICE_NAME
    if [ "${SERVICE_NAME_PARAM}"x != ""x ]; then
        SERVICE_NAME="${SERVICE_NAME_PARAM}"
        log_info "Set SERVICE_NAME from param \"${SERVICE_NAME_PARAM}\""
        return
    fi


    # 使用配置文件中的SERVICE_NAME
    if [ -f "${APP_CONF_DIR}/service.properties" ];then
        SERVICE_NAME="$(get_property_in_file "SERVICE_NAME" "${APP_CONF_DIR}/service.properties" )"
        eval SERVICE_NAME=${SERVICE_NAME}

        if [ -z "${SERVICE_NAME}" ]; then
            log_warn "SERVICE_NAME not found in service.properties."
        else
            log_info "Set SERVICE_NAME from service.properties \"${SERVICE_NAME}\""
        fi
    else
        log_warn "service config '${APP_CONF_DIR}/service.properties' not exists."
    fi

    #if [ -z "${SERVICE_NAME}" ]; then
    #    SERVICE_NAME="${APP_MAIN_CLASS##*\.}"
    #    log_info "Set SERVICE_NAME from short MAIN_CLASS name \"${SERVICE_NAME}\""
    #fi

    if [ -z "${SERVICE_NAME}" ]; then
        log_error "SERVICE_NAME not found"
        exit 1;
    fi

}

detonator::init_service_id()
{
    if [ -z ${SERVICE_ID} ] && [ ! -z ${SERVICE_ID_PARAM} ]; then
        log_info "Set service id from param: ${SERVICE_ID_PARAM}"
        SERVICE_ID=${SERVICE_ID_PARAM}
    fi

    if [ -z ${SERVICE_ID} ] && [ ! -z ${STATIC_SERVICE_ID_PARAM} ]; then
        SERVICE_ID="st$(echo "$(hostname) ${APP_HOME} ${SERVICE_NAME} ${MAIN_CLASS}" | md5sum | awk '{print $1}' | cut -c 1-10)"
        log_info "Generate static service id: ${SERVICE_ID}"
    fi

    if [ -z ${SERVICE_ID} ]; then
        if  [ "$(printenv IS_IN_CONTAINER)"x = "truex" ];then
            SERVICE_ID=$(cat /proc/1/cpuset | awk -F / '{print $(NF-0)}' | cut -c 1-12)
            log_info "Set service id from container id: ${SERVICE_ID}"
        else
            SERVICE_ID=$(date '+%Y%m%d%H%M%S%N')
            log_info "Generate service id randomly: ${SERVICE_ID}"
        fi
    fi


    if [ -z ${SERVICE_ID} ]; then
        log_error "Service id init failed"
        exit 1
    fi

}



detonator::init_log_dir()
{
    local default_log_base_dir="${APP_HOME}/logs"
    local default_log_dir="${default_log_base_dir}/${SERVICE_NAME}/${SERVICE_ID}"

    # 是否开启日志选盘
    if [ -z "${ENABLE_LOG_DIR_ELECTION_PARAM}" ] && [ "$(printenv IS_IN_CONTAINER)"x != "truex" ]; then
        # 如果参数中没有开启选盘，并且在非容器环境下，则使用本地日志路径
        APP_LOG_BASE_DIR=${default_log_base_dir}
        APP_LOG_DIR=${default_log_dir}
        log_info "Log dir election disabled. Use default log dir: ${APP_LOG_DIR}"
        return
    fi

    if [ ! -z "${CANDIDATE_LOG_DIRS_PARAM}" ]; then
        CANDIDATE_LOG_DIRS=${CANDIDATE_LOG_DIRS_PARAM}
    else
        CANDIDATE_LOG_DIRS=/mnt/sd*/ocean-logs
    fi

    log_info "Start log dir election, the election rule is: ${CANDIDATE_LOG_DIRS}"

    local available_candidate_log_dirs=()

    # 从参数中拿到所有日志候选目录，通过FIND找到所有配置文件，拿到所有配置文件中包含is_enabled=true的所有路径（候选目录）
    for i in $(find ${CANDIDATE_LOG_DIRS} -maxdepth 1 -name ${LOG_DIR_CONFIG_FILE:="log-dir.config"})
    do
        if [ ! -z $(grep "isEnabled=true" ${i}) ]; then
            local the_dir_name=$(dirname ${i})

            # 测试候选目录可用性
            if [ ! -z $(test_dir_available ${the_dir_name}) ]; then
                available_candidate_log_dirs+=(${the_dir_name})
                log_info "Add candidate log dir: \"${the_dir_name}\""
            else
                log_info "Log dir \"${i}\" not available"
            fi
        else
            log_info "Log dir \"${i}\" not enable"
        fi
    done



    local max=${#available_candidate_log_dirs[@]}


    if [ ${max} -eq 0 ]; then
        # 如果没有匹配的日志目录，则使用本地目录
        APP_LOG_BASE_DIR=${default_log_base_dir}
        APP_LOG_DIR=${default_log_dir}
        log_info "Candidate log dir size is 0, use default log dir: ${APP_LOG_DIR}"
        return
    fi

    # 选择日志目录
    local random_index=$(( $RANDOM % $max ))
    APP_LOG_BASE_DIR=${available_candidate_log_dirs[${random_index}]}
    log_info "Pick log dir in candidate log dirs: ${APP_LOG_BASE_DIR}"

    APP_LOG_DIR="${APP_LOG_BASE_DIR}/${SERVICE_NAME}/${SERVICE_ID}"
    mkdir -p ${APP_LOG_DIR}
    if [ ! -d ${APP_LOG_DIR} ]; then
        log_error "Make app log dir \"${APP_LOG_DIR}\" failed, use default log dir: ${default_log_dir}"
        APP_LOG_BASE_DIR=${default_log_base_dir}
        APP_LOG_DIR=${default_log_dir}
        return
    fi
    if [ ! -w ${APP_LOG_DIR} ]; then
        log_error "Log dir \"${APP_LOG_DIR}\" has no writable permission, use default log dir: ${APP_LOG_DIR}"
        APP_LOG_BASE_DIR=${default_log_base_dir}
        APP_LOG_DIR=${default_log_dir}
        return
    fi
    log_info "Service log dir: ${APP_LOG_DIR}"


    if [[ -L "${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}" && -d "${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}" ]]; then
        log_info "Remove exists symlink: ${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}"
        rm ${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}
    fi

    if [ ! -e "${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}" ]; then
        mkdir -p ${APP_HOME}/logs/${SERVICE_NAME}
        ln -s ${APP_LOG_DIR} ${APP_HOME}/logs/${SERVICE_NAME}/
        log_info "Create symlink from target \"${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}\", to link \"${APP_HOME}/logs/${SERVICE_NAME}/\""
    else
        log_warn "Log directory \"${APP_HOME}/logs/${SERVICE_NAME}/${SERVICE_ID}\" has been already exists, could not create symlink"
    fi
}

detonator::download_overwatch_agent(){
        log_info "Download overwatch agent from $1"

        wget -q --spider "$1"
        local check_result=$?
        if [[ ${check_result} -gt 0 ]];then
            log_error "Check "$2" agent download url failed."
        else
            wget -O "${APP_HOME}/plugins/"$2"/"$3 "$1"

            if [ -f "${APP_HOME}/plugins/"$2"/"$3 ]; then
                if [ $(ls -l ${APP_HOME}/plugins/$2/$3 | awk '{print $5}') -gt 3000000  ]; then
                    log_info "Download remote overwatch agent jar \"$3\" successfully"
                else
                    log_error "Download error, downloaded "$2" agent file size < 3m"
                    rm -f ${APP_HOME}/plugins/$2/$3
                fi
            else
                log_warn "Remote "$2" agent downloaded, but the file not found in local directory"
            fi
        fi
}

detonator::find_component_api_jar_from_classpath() {
    IFS=:
    local classpath_array=(${APP_CLASSPATH})
    for key in "${!classpath_array[@]}"; do
            classpath_component_api_jar_file=$(find ${classpath_array[$key]} -name $1"-*.jar" | sort -V -k1 | tail -1)
            if [ ! -z ${classpath_component_api_jar_file} ]; then
                log_info "Find overwatch-api jar: ${classpath_component_api_jar_file}"
                IFS=''
                return
            fi
    done
    IFS=''
}

detonator::init_component_agent() {
    OVERWATCH_JAVA_OPTS=""

    if [ "${ATTACH_OVERWATCH_AGENT}"x == "falsex"  ]; then
        log_info "Attach overwatch disabled"
        return
    fi

    log_info "Start init Overwatch agent"
    # 获取项目依赖的组件名称
    component_agent_name=$(curl -s ${COMPONENT_NAME_GET_URL}${SERVICE_NAME})
    if [ -z ${component_agent_name} ]; then
        log_error "component agent name is empty!"
        return
    fi
    component_name=${component_agent_name%*-agent}
    component_api_name=${component_name}"-api"

    # 创建组件目录
    if [ ! -d "${APP_HOME}/plugins/${component_name}" ]; then
        mkdir -p "${APP_HOME}/plugins/${component_name}"
    fi

    # 获取项目依赖的组件api版本
    detonator::find_component_api_jar_from_classpath ${component_api_name}

    # 获取项目需要下载的组件URL
    manager_url=${MANAGER_REQUEST_URL_TEMPLATE}${SERVICE_NAME}"&componentName="${component_agent_name}"&apiName="
    if [ ! -z ${classpath_component_api_jar_file} ]; then
        component_api_jar_name=${classpath_component_api_jar_file##*/}
        manager_url=${manager_url}${component_api_jar_name}
    fi
    agent_download_url=$(curl -s ${manager_url})

    # 下载组件到classpath
    local component_agent_file=${agent_download_url##*/}
    if [ -f ${APP_HOME}/plugins/${component_name}/${component_agent_file} ]; then
        downloaded_overwatch_agent_file_name="${component_agent_file}"
        log_info "Local overwatch agent \"${component_agent_file}\" exists, ignore download"
    else
        detonator::download_overwatch_agent ${agent_download_url} ${component_name} ${component_agent_file}
    fi

    if [ ! -z "${component_agent_file}" ]; then
        OVERWATCH_JAVA_OPTS="-javaagent:${APP_HOME}/plugins/${component_name}/${component_agent_file}"
        log_info "Use local overwatch agent: ${component_agent_file}"
    else
        log_error "Overwatch agent init failed"
        exit 1
    fi

}

detonator::init_classpath(){
    APP_CLASSPATH=${APP_HOME}/lib:${APP_HOME}/lib/*
    if [ "${CLASSPATH_PARAM}"x = ""x ]; then
        log_info "Set classpath from default: ${APP_CLASSPATH}"
    else
        APP_CLASSPATH=$(eval "echo \"${CLASSPATH_PARAM}\"")
        log_info "Set classpath from param: \"${APP_CLASSPATH}\""
    fi
}

detonator::init_java_opts(){

    log_info "Create tmp java.io.tmpdir \"${APP_HOME}/tmp/${SERVICE_NAME}/${SERVICE_ID}\""
    mkdir -p ${APP_HOME}/tmp/${SERVICE_NAME}/${SERVICE_ID}

    # application jvm args
    DEFAULT_JAVA_OPTS="-Dapp.mode=production \
        -Dapp.path=${APP_HOME} \
        -Dapp.service=${SERVICE_NAME} \
        -Ddebug.mode=false \
        -Docean.service.name=${SERVICE_NAME} \
        -Docean.service.id=${SERVICE_ID} \
        -Docean.home.dir=${APP_HOME} \
        -Docean.log.dir=${APP_LOG_DIR} \
        -Djava.io.tmpdir=${APP_HOME}/tmp/${SERVICE_NAME}/${SERVICE_ID}"

    # log4j2 config
    DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} \
        -Dlog4j.configurationFile=${APP_CONF_DIR}/log4j2.xml \
        -Dlogging.config=${APP_CONF_DIR}/log4j2.xml"



    if [ $(java -version 2>&1 | grep -i version | sed 's/.*version ".*\.\(.*\)\..*"/\1/; 1q') -lt 8 ]; then
        # java8以下版本添加参数
        DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} \
            -XX:PermSize=128m \
            -XX:MaxPermSize=256m"

    else
        # java8及以上版本添加（java8小版本必须大于9x）
        DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} \
            -XX:+ExitOnOutOfMemoryError"
    fi


    log_suffix=$(date '+%Y%m%d%H%M%S')
    mkdir -p ${APP_LOG_DIR}/gc

    # add gc opts 注意jvm.options不是在APP_CONF_DIR里，永远在${APP_HOME}/conf
    APP_JAVA_OPTS="${DEFAULT_JAVA_OPTS} $(parse_jvm_options "${APP_HOME}/conf/jvm.options") $(parse_jvm_options "${APP_CONF_DIR}/service.vmoptions") ${JAVA_OPTS_PARAM}"

    APP_JAVA_OPTS="$(eval "echo \"${APP_JAVA_OPTS}\"")"

    log_info "Service java opts: "
    echo "$APP_JAVA_OPTS" | awk '{ gsub(" -", "\n-") } 1' | tee -a "${DETONATOR_LOG_FILE}"

    #log_info "Service java opts: ${APP_JAVA_OPTS}"
}


detonator::init_static_info(){

    detonator::check_java_home

    detonator::init_app_home

    detonator::init_conf_dir

    detonator::init_main_class

    detonator::init_service_name
}

detonator::init(){

    detonator::init_static_info

    detonator::terminate_if_started

    detonator::init_service_id

    detonator::init_log_dir

    detonator::init_classpath

    detonator::init_component_agent

    detonator::init_java_opts

}

detonator::get_service_pid() {

    local javaps=`${JAVA_HOME}/bin/jps -lv |  grep -w '\-Docean.service.name='${SERVICE_NAME} | grep -w '\-Docean.home.dir='${APP_HOME}`

    if [ -n "${javaps}" ]; then
        SERVICE_PID=`echo ${javaps} | awk '{print $1}'`
    else
        SERVICE_PID=0
    fi
}

detonator::terminate_if_started() {

    detonator::get_service_pid

    if [ ${SERVICE_PID} -ne 0 ]; then
        log_error "Service '${SERVICE_NAME}' has been already started."
        exit 1;
    fi
}

# 启动应用
detonator::detonate() {

    local start_cmd="${JAVA_HOME}/bin/java -cp ${APP_CLASSPATH} ${APP_JAVA_OPTS}"

    if [ ! -z "${OVERWATCH_JAVA_OPTS}" ]; then
        start_cmd="${start_cmd} ${OVERWATCH_JAVA_OPTS}"
    fi

    start_cmd="${start_cmd} ${APP_MAIN_CLASS} $(eval "echo \"${APP_ARGS_PARAM}\"")"

    if [ ! -z "${APP_START_IN_DAEMON}" ]; then
        start_cmd="nohup ${start_cmd} > ${APP_LOG_DIR}/console.out 2>&1 &"
        log_info "Start service in daemon mode"
    else
        start_cmd="${start_cmd} &"
    fi
    log_info "Service start command: ${start_cmd}"
    log_info "Service starting, please wait..."

    # copy stdout log to service log file
    if [ -z ${DRY_RUN} ]; then
        mkdir -p ${APP_LOG_DIR}/business
        cat ${DETONATOR_LOG_FILE} >> ${APP_LOG_DIR}/business/current.log
        TEMP_LOG_FILE=${DETONATOR_LOG_FILE}
        DETONATOR_LOG_FILE=${APP_LOG_DIR}/business/current.log


        if [ ! -z "${APP_START_IN_DAEMON}" ]; then
            eval "${start_cmd}"
        else
            trap 'log_info "Caught kill signal ${SERVICE_PID}";detonator::notifyShutdown;kill -TERM ${SERVICE_PID}' TERM INT
            eval "${start_cmd}"
        fi
    fi
}

detonator::check_start_success() {

    for i in {1..5}; do

        detonator::get_service_pid

        if [ ${SERVICE_PID} -ne 0 ]; then
            log_info "Service start successful, pid:${SERVICE_PID}"
            return
        else
            log_info "Wait for service \"${SERVICE_NAME}\" start..."
        fi
        sleep 1
    done

    echo "Wait for service \"${SERVICE_NAME}\" start timeout，please check it manually"

}

detonator::status(){

    detonator::init_static_info

    if [ -z "${SERVICE_NAME}" ]; then
        log_info "Service name should be set in service.properties or args"
        exit 1;
    fi

     detonator::get_service_pid

    if [ ${SERVICE_PID} -ne 0 ]; then
        log_info "Service \"${SERVICE_NAME}\" is running with pid:${SERVICE_PID}"
    else
        log_info "Service \"${SERVICE_NAME}\" is not started."
        exit 1
    fi


    local service_info_file=${APP_HOME}/run/${SERVICE_PID}/info
    if [ -f ${service_info_file} ]; then
        local serverPort=$(cat ${service_info_file} | awk -F 'serverPort' '{print $2}' | awk -F ',' '{print $1}' | awk -F ':' '{print $2}' | xargs )
        local isReadyUri=$(cat ${service_info_file} | awk -F 'isReadyUri' '{print $2}' | awk -F ',' '{print $1}' |awk -F '"' '{print $(NF-1)}' | xargs )
        local isHealthyUri=$(cat ${service_info_file} | awk -F 'isHealthUri' '{print $2}' | awk -F ',' '{print $1}' |awk -F '"' '{print $(NF-1)}' | xargs )
        local overwatch_port=$(cat ${service_info_file} | awk -F 'overwatchPort' '{print $2}' | awk -F ',' '{print $1}' | awk -F ':' '{print $2}' | xargs )
    else
        log_info "Service info file [\"${APP_HOME}/run/${SERVICE_PID}/info\"] not found, return success"
        exit 0
    fi

    if [[ overwatch_port -lt 0 ]]; then
        log_info "Overwatch port not found, exit 1"
        exit 1
    fi

    # 检查应用是否健康（可用）
    local service_is_healthy=true
    local app_check_healthy_url=http://localhost:${overwatch_port}/stats/healthCheck
    log_info "CheckHealthy uri: ${isHealthyUri}"
    local app_check_healthy_code=$(curl --connect-timeout 3 -s -o /dev/null -w "%{http_code}" ${app_check_healthy_url})
    if [[ ! 200 == ${app_check_healthy_code} ]]; then
        service_is_healthy=false
        log_info "Application is not healthy! return code: ${app_check_healthy_code}, Message:"
        curl --connect-timeout 3 ${app_check_healthy_url}
        echo ""
    else
        log_info "Application is healthy."
    fi

    # 检查应用是否准备就绪
    local service_is_ready=true
    local app_check_ready_url=http://localhost:${overwatch_port}/stats/readyCheck
    log_info "CheckReady uri: ${isReadyUri}"
    local app_check_ready_code=$(curl --connect-timeout 3 -s -o /dev/null -w "%{http_code}" ${app_check_ready_url})
    if [[ ! 200 == ${app_check_ready_code} ]]; then
        service_is_ready=false
        log_info "Application is not ready! return code: ${app_check_ready_code}, Message:"
        curl --connect-timeout 3 ${app_check_ready_url}
        echo ""
    else
        log_info "Application is ready."
    fi



    if [[ ! ${service_is_healthy} == true && ${CHECK_HEALTHY_PARAM} == true ]]; then
        log_info "Check healthy failed, return code 1"
        exit 1
    fi

    if [[ ! ${service_is_ready} == true && ${CHECK_READY_PARAM} == true ]]; then
        log_info "Check ready failed, return code 1"
        exit 1
    fi

}


detonator::start()
{
    SAVE_DETONATOR_FILE=true

    detonator::init

    detonator::detonate

    detonator::check_start_success

    rm -rf ${TEMP_LOG_FILE}

    if [ ! -z ${DRY_RUN} ]; then
        log_info "Dry run finished"
    elif [ -z "${APP_START_IN_DAEMON}" ]; then
        wait ${SERVICE_PID}
        trap - TERM INT
        wait ${SERVICE_PID}
        log_info "Program exit with exit code $?"
    fi

}

detonator::stop()
{

    detonator::init_static_info

    if [ -z "${SERVICE_NAME}" ]; then
        echo "Service name should be set in service.properties or args"
        exit 1;
    fi

     detonator::get_service_pid

     if [ ${SERVICE_PID} -ne 0 ]; then

        log_info "Service is running with pid:${SERVICE_PID}, stopping..."

        detonator::notifyShutdown
        if [ ! -z ${APP_FORCE_KILL} ]; then
            log_info "Force kill!!"
            kill -9 ${SERVICE_PID}
        else
            kill ${SERVICE_PID}
        fi


        for i in {1..10}; do

            detonator::get_service_pid

            if [ ${SERVICE_PID} -eq 0 ]; then
                log_info "Service stopped successfully."
                exit 0
            else
                log_info "Stopping..."
            fi
            sleep 1
        done


        if [ ! -z ${APP_FORCE_KILL} ]; then
            log_info "Force kill failed"
            exit 1
        else
            log_info "Stop service failed, please stop with args \"-f\" to force kill"
            exit 1
        fi

    else
        log_info "Service is not started."
    fi
}

detonator::restart()
{

    echo  "Unsupported Operation"
    exit 0;
    # check service name first

    detonator::stop

    detonator::start

}


detonator::shortcut() {
    if [ -z "${SHORTCUT_COMMAND}" ]; then
        log_error "Shortcut command should not be null."
        exit 1
    fi

    if [ -z "${SHORTCUT_NAME}" ]; then
        log_error "Shortcut name should not be null."
        exit 1
    fi

    local shortcut_file=${THIS_FILE_DIR}/${SHORTCUT_NAME}

    if [ -f "${shortcut_file}" ]; then
        log_error "Shortcut file ${shortcut_file} has been already exists"
        exit 1
    fi

    log_info "Create shortcut file: ${shortcut_file}"

    echo "#!/bin/bash" >> ${shortcut_file}
    echo "/bin/bash ${THIS_FILE_PATH} ${SHORTCUT_COMMAND}" >> ${shortcut_file}
    chmod 755 ${shortcut_file}

}

detonator::notifyShutdown() {
    log_info "start notify unregister!"
    local service_info_file=${APP_HOME}/run/${SERVICE_PID}/info
    local overwatch_port=$(cat ${service_info_file} | awk -F 'overwatchPort' '{print $2}' | awk -F ',' '{print $1}' | awk -F ':' '{print $2}' | xargs )
    local service_shutdown_url="http://localhost:${overwatch_port}/registry/unregister"
    local unhealth_code=$(curl --connect-timeout 3 -s "${service_shutdown_url}")
    sleep 5s
    if [ "${unhealth_code}"x = "truex" ]; then
        log_info "notify unregister success!"
    else
        log_error "notify unregister fail!"
    fi
}


main "$@"