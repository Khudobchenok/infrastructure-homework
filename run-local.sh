#!/bin/bash

# Включить режим "exit on error"
set -e

# Переменная для проверки
logLevel=$1

# Проверка наличия аргумента
if [ -z "$logLevel" ]; then
    echo "Error: No log level provided."
    logLevel=INFO
fi

# Проверка значения переменной
if [[ "$logLevel" != "TRACE" && "$logLevel" != "DEBUG" && "$logLevel" != "INFO" && "$logLevel" != "WARN" && "$logLevel" != "ERROR" && "$logLevel" != "FATAL" && "$logLevel" != "OFF" ]]; then
    echo "Error: Invalid log level. Allowed values are TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF."
    exit 1
fi

java -jar build/libs/*.jar --logging.level.root="$LOG_LVL"
