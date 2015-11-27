#!/bin/bash

user=$1;
ip=$2;
password=$3;
cmd=$4;
filepath=$5;
dest_dir=$6;
#echo "$user $ip $password $cmd $filepath"

if [ "$cmd" = "pull" ]
then
    expect /home/abhishek/work/cas/git/resources/scriptengine/transferFile.exp $user $ip $password getModTime "$filepath" &
    my_pid=$!
fi

expect /home/abhishek/work/cas/git/resources/scriptengine/transferFile.exp $user $ip $password $cmd "$filepath" $dest_dir
retval=$?
echo "Return value :$retval"; # Printing returned value from Expect

if [ "$cmd" = "pull" ]
then
    wait $my_pid
    my_status=$?
    echo "Return value :getModTime :$my_status"
fi
exit $retval;
