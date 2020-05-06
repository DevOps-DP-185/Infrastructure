#!/bin/bash

array_env=$( ls /home/env/)
array_liquibase=$( ls /opt/liquibase/kick-scooter/)
var1=$( sed -n 1p /home/pass/env.txt)
var2=$( sed -n 2p /home/pass/env.txt)
var3=$( sed -n 3p /home/pass/env.txt)

for i in ${array_env[@]}

do
ip=$( cat /home/env/$i | grep jdbc | awk -F"/" '{ print $3}')
pass=$( cat /home/env/$i | grep 'DATABASE_PASSWORD\|SQL_PASSWORD' |  awk -F"=" '{ print $2}')
user=$( cat /home/env/$i | grep 'DATABASE_USERNAME\|SQL_USERNAME' |  awk -F"=" '{ print $2}')

sed -i  "s/${ip}/${var1}/g; s/${pass}/${var2}/; s/${user}/${var3}/" /home/env/$i
done


for i in ${array_liquibase[@]}

do
ip=$( cat /opt/liquibase/kick-scooter/$i/liquibase.properties | grep jdbc | awk -F"/" '{ print $3}')
pass=$( cat /opt/liquibase/kick-scooter/$i/liquibase.properties | grep 'password' |  awk -F":" '{ print $2}')
user=$( cat /opt/liquibase/kick-scooter/$i/liquibase.properties | grep 'username' |  awk -F":" '{ print $2}')

sed -i "s/${ip}/${var1}/g; s/${pass}/${var2}/; s/${user}/${var3}/" /opt/liquibase/kick-scooter/$i/liquibase.properties
done
