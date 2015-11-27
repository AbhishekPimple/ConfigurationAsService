#!/bin/bash

FILENAME=$1
count=0

cat $FILENAME | while read LINE

do
   count=count+1
   $LINE &
#   mypid=$!
done

#echo -e "\nTotal $count Lines read"

#    wait $my_pid
#    my_status=$?
#    echo "MULTIPLE:Return value :getModTime :$my_status"
