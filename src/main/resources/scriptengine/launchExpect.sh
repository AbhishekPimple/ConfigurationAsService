
#!/bin/bash
path=$1;
user=$2;
ip=$3;
password=$4;
cmd=$5;
filepath=$6;
dest_dir=$7;

if [ "$cmd" = "pull" ]
then
    expect $1transferFile.exp $user $ip $password getModTime "$filepath" &
    my_pid=$!
fi

if [ "$cmd" = "restart" ]
then
    filepath=`echo $filepath | tr '#' ' '`;
fi

expect $1transferFile.exp $user $ip $password $cmd "$filepath" $dest_dir
retval=$?
echo "Return value of ip:$ip =$retval"; # Printing returned value from Expect

if [ "$cmd" = "pull" ]
then
    wait $my_pid
    my_status=$?
    echo "Return value :getModTime of ip:$ip =$my_status"
fi
exit $retval;