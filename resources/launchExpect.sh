#!/bin/bash

user=$1;
ip=$2;
password=$3;
cmd=$4;
filepath=$5;
dest_dir=$6;
expect transferFile.exp $user $ip $password $cmd $filepath $dest_dir
retval=$?
echo "Expect's return value : $retval"; # Printing returned value from Expect
exit $retval;
