#!/bin/bash

FILENAME=$1
count=0

cat $FILENAME | while read LINE

do
   count=count+1
   $LINE &
done
