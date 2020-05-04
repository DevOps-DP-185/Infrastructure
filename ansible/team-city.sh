#!/bin/bash

sudo apt update
sudo apt upgrade -y

sudo apt install git -y
sudo apt install maven -y
sudo apt install postgresql-client -y

#Install Java 8
sudo apt install apt-transport-https ca-certificates wget dirmngr gnupg software-properties-common -y
wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | sudo apt-key add -
sudo add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/
sudo apt update
sudo apt install adoptopenjdk-8-hotspot -y

echo 'cat >> /etc/environment <<EOL
JAVA_HOME=/usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/jre/bin/java
EOL' | sudo -s
source /etc/environment

#Install Docker
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io -y

#Install TeamCity
cd /opt/
sudo wget https://download.jetbrains.com/teamcity/TeamCity-2019.2.3.tar.gz
sudo tar -xzf TeamCity-2019.2.3.tar.gz
cat /opt/TeamCity/conf/server.xml | sed -e "s/8111/80/" > ~/server1.xml
sudo mv ~/server1.xml /opt/TeamCity/conf/server.xml

sudo useradd teamcity
sudo chown -R teamcity:teamcity /opt/TeamCity

echo 'cat >> /etc/init.d/teamcity <<EOL
#!/bin/bash

export TEAMCITY_DATA_PATH="/opt/TeamCity/.BuildServer"

case \$1 in

start)
  start-stop-daemon --start  -c teamcity --exec /opt/TeamCity/bin/runAll.sh start
 ;;
stop)
  start-stop-daemon --start -c teamcity --exec  /opt/TeamCity/bin/runAll.sh stop
 ;;
 esac

exit 0
EOL' | sudo -s

sudo chmod +x /etc/init.d/teamcity
sudo update-rc.d teamcity defaults
sudo bash /opt/TeamCity/bin/runAll.sh start

#Install TeamCity Agent
sudo apt install unzip
sudo wget http://team-city.svagworks.me/update/buildAgent.zip
sudo mkdir BuildAgent
sudo unzip buildAgent.zip -d BuildAgent/
cat /opt/BuildAgent/conf/buildAgent.dist.properties | sed -e "s/localhost:8111/team-city.svagworks.me/; s/name=/name=Demo4/" > ~/buildAgent.properties
sudo mv ~/buildAgent.properties /opt/BuildAgent/conf/buildAgent.properties

echo 'cat >> /etc/init.d/buildAgent <<EOL
#!/bin/sh
### BEGIN INIT INFO
# Provides:          TeamCity Build Agent
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start build agent daemon at boot time
# Description:       Enable service provided by daemon.
### END INIT INFO
USER="teamcity"
 
case "\$1" in
start)
 su - $USER -c "cd /opt/BuildAgent/bin ; ./agent.sh start"
;;
stop)
 su - $USER -c "cd /opt/BuildAgent/bin ; ./agent.sh stop"
;;
*)
  echo "usage start/stop"
  exit 1
 ;;
 
esac
 
exit 0
EOL' | sudo -s

sudo chmod 755 /etc/init.d/buildAgent
sudo update-rc.d buildAgent defaults

sudo bash /opt/BuildAgent/bin/agent.sh start

#Intall Java 11 (needed for java app)
sudo apt install openjdk-11-jdk -y

#Install Liquibase
sudo mkdir /opt/liquibase && cd /opt/liquibase
sudo wget https://github.com/liquibase/liquibase/releases/download/v3.8.8/liquibase-3.8.8.tar.gz 
sudo tar -xvf liquibase-3.8.8.tar.gz
echo "export PATH=/opt/liquibase:$PATH" >> ~/.bashrc && source ~/.bashrc
liquibase --versiont
