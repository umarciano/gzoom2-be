#!/bin/bash
# chkconfig: 345 80 20
# description: Pleiade gzoom2-be-service
### BEGIN INIT INFO
# Provides: {{instance_name}}
# Required-Start: $network postgresql-9.3
# Required-Stop: $network postgresql-9.3
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

# GZOOM multi-environment: hardcoded per questo server.
# Cambiare in "prod" sul server di produzione, lasciare vuoto per sviluppo locale.
# Non affidarsi a GZOOM_ENV da ~/.bashrc perchÃ© 'sudo service' non eredita l'environment utente.
GZOOM_ENV_VALUE="collaudo"

log_success_msg() {
  echo "$*"
}

log_failure_msg() {
  echo "$*"
}

check_proc() {
  pgrep -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null
}

check_health() {
  curl $SERVICE_HEALTH_URL 2>&1 | grep '"status":"UP"' > /dev/null 2>&1
}

start_service() {
  if [ "${CUR_USER}" != "root" ] && [ "${CUR_USER}" != "{{ service_username }}" ]; then
    log_failure_msg "gzoom2-be-service can only be started as 'root' or '{{ service_username }}' user."
    exit 4
  fi

  check_proc
  if [ $? -eq 0 ]; then
    log_success_msg "gzoom2-be-service already running."
    exit 0
  fi

        log_success_msg "Starting gzoom2-be-service with GZOOM_ENV=${GZOOM_ENV_VALUE:-<not set>}"  if [ "${CUR_USER}" == "root" ]; then
    # Nota: -DGZOOM_ENV passato come valore literal perchÃ© 'su -' resetta l'environment
    nohup /bin/su - root -c "LOG_DIR=/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be java \
      -Dgzoom.conf.dir='/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be/config' \
      -DGZOOM_ENV='${GZOOM_ENV_VALUE}' \
      -jar $SERVICE_DIR/$APP_NAME.jar" < /dev/null >> $LOGS 2>&1 &
  else
    LOG_DIR=/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be \
    nohup java \
      -Dgzoom.conf.dir="/opt/gzoom-app/GZOOM_CARDARELLI/workspace/gzoom2-be/config" \
      -DGZOOM_ENV="${GZOOM_ENV_VALUE}" \
      -jar $SERVICE_DIR/$APP_NAME.jar < /dev/null >> $LOGS 2>&1 &
  fi

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
  log_success_msg "Please wait health check. It might take few second if the service is starting..."
  attempts=0
  check_health
  while [ $? -gt 0 ] && [ $attempts -lt 30 ]
  do
    attempts=$[$attempts + 1]
    sleep 1s
    check_health
  done
  if [ $attempts -gt 29 ]
    then
    log_failure_msg "Service not started yet, please check logs: $LOGS"
  else
    log_success_msg "gzoom2-be-service process health sucessful."
  fi
}

stop_service() {
  if [ "${CUR_USER}" != "root" ] && [ "${CUR_USER}" != "{{ service_username }}" ]; then
    log_failure_msg "You do not have permission to stop the gzoom2-be-service"
    exit 4
  fi

  check_proc

  if [ $? -eq 0 ]; then
    log_success_msg "Attempting graceful shutdown http actuator..."

    attempts=0
    while pkill -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null 2>&1
    do
      attempts=$[$attempts + 1]
      log_success_msg "Attempt $attempts ..."
      if [ $attempts -gt 10 ]
        then
        log_failure_msg "Service not shutdown yet, attempting forceful shutdown..."
        pkill -9 -u root -f $SERVICE_DIR/$APP_NAME.jar >/dev/null 2>&1
      fi
      sleep 1s
    done

    until [ $? -ne 0 ]; do
      sleep 1
      check_proc
    done

    check_proc
    if [ $? -eq 0 ]; then
      log_failure_msg "Error stopping gzoom2-be-service."
      exit -1
    else
      log_success_msg "Stopped gzoom2-be-service."
    fi
  else
    log_failure_msg "gzoom2-be-service is not running or you don't have permission to stop it"
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
  start)
  start_service
  ;;
  stop)
  stop_service
  ;;
  restart)
  stop_service
  start_service
  ;;
  status)
  service_status
  ;;
  force-reload)
  stop_service
  start_service
  ;;
  health)
  service_health
  ;;
  *)
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
esac

exit 0
