#!/bin/bash
set -e
service mysql start
mysql < /mysql/init.sql
service mysql stop