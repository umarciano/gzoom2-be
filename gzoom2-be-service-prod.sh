#!/bin/bash
# description: Pleiade gzoom2-be-service
### BEGIN INIT INFO
# Provides: gzoom2-be-service
# Required-Start: $network
# Required-Stop: $network
# Default-Start: 3 4 5
# Default-Stop: 0 1 2 6
# Description: Starts the Pleiade gzoom2-be-service
### END INIT INFO

# Nome del JAR senza estensione (deve corrispondere al file .jar in SERVICE_DIR)
APP_NAME=rest-boot-2.9.5
APP_PORT=8081

SERVICE_DIR=/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be
SERVICE_BASE_URL=http://localhost:$APP_PORT
SERVICE_HEALTH_URL=$SERVICE_BASE_URL/health
SERVICE_SHUTDOWN_URL=$SERVICE_BASE_URL/shutdown

CUR_USER=`whoami`
LOGS=$SERVICE_DIR/gzoom2-be-service.log

# GZOOM multi-environment: hardcoded per il server di PRODUZIONE.
GZOOM_ENV_VALUE="prod"

# Java 11 richiesto da Spring Boot 2.1 + spring-ldap (incompatibile con Java 17)
JAVA_HOME=/usr/lib/jvm/java-11-amazon-corretto

log_success_msg() { echo "$*"; }
log_failure_msg() { echo "$*"; }

check_proc() {
  pgrep -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null
}

check_health() {
  curl $SERVICE_HEALTH_URL 2>&1 | grep '"status":"UP"' > /dev/null 2>&1
}

start_service() {
  if [ "${CUR_USER}" != "root" ]; then
    log_failure_msg "gzoom2-be-service can only be started as root."
    exit 4
  fi

  check_proc
  if [ $? -eq 0 ]; then
    log_success_msg "gzoom2-be-service already running."
    exit 0
  fi

  log_success_msg "Starting gzoom2-be-service with GZOOM_ENV=${GZOOM_ENV_VALUE}"
  nohup /bin/su - root -c "LOG_DIR=/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be /usr/lib/jvm/java-11-amazon-corretto/bin/java \
    -Dgzoom.conf.dir='/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be/config' \
    -DGZOOM_ENV='prod' \
    -jar /opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be/rest-boot-2.9.5.jar" < /dev/null >> $LOGS 2>&1 &

  sleep 1
  check_proc
  if [ $? -eq 0 ]; then
    log_success_msg "gzoom2-be-service process is running."
  else
    log_failure_msg "gzoom2-be-service process is not running."
    exit -1
  fi
}

service_health() {
  attempts=0
  check_health
  while [ $? -gt 0 ] && [ $attempts -lt 30 ]; do
    attempts=$[$attempts + 1]
    sleep 1s
    check_health
  done
  if [ $attempts -gt 29 ]; then
    log_failure_msg "Service not started yet, please check logs: $LOGS"
  else
    log_success_msg "gzoom2-be-service process health sucessful."
  fi
}

stop_service() {
  check_proc
  if [ $? -eq 0 ]; then
    attempts=0
    while pkill -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null 2>&1; do
      attempts=$[$attempts + 1]
      log_success_msg "Attempt $attempts ..."
      if [ $attempts -gt 10 ]; then
        pkill -9 -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null 2>&1
      fi
      sleep 1s
    done
    log_success_msg "Stopped gzoom2-be-service."
  else
    log_failure_msg "gzoom2-be-service is not running."
  fi
}

service_status() {
  check_proc
  if [ $? -eq 0 ]; then
    log_success_msg "gzoom2-be-service is running."
  else
    log_failure_msg "gzoom2-be-service is stopped."
    exit 3
  fi
}

case "$1" in
  start)   start_service ;;
  stop)    stop_service ;;
  restart) stop_service; start_service ;;
  status)  service_status ;;
  health)  service_health ;;
  *)       echo "Usage: $0 {start|stop|restart|status|health}"; exit 1 ;;
esac

exit 0