#!/bin/bash
#########################################
PROX_BIN=/home/proximus/bin
$PROX_BIN/bash_scripts/kill_proximus.sh
rm -r /home/proximus/logs
$PROX_BIN/start_proximus_client.sh &