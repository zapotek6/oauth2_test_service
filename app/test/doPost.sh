#!/usr/bin/env bash

####### MAIN
API_SERVER="http://api.labfour.co.uk"
API_PATH="/"
API_FILE="getNote.json"

if [ ! -z "$1" ]; then
  API_SERVER=$1
fi

if [ ! -z "$2" ]; then
  API_FILE=$2
fi

URL="${API_SERVER}${API_PATH}"

echo "curl -X POST -d@${API_FILE} -H 'Content-Type: application/json' ${URL}"
curl -X POST -d@${API_FILE} -H "Content-Type: application/json" ${URL}
