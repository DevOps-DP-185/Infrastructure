#! /bin/bash

# Array of services defined in docker-compose
services=("zookeeper" "kafka" "discovery" "gateway" "identity" "vehicle" "trip" "payment" "messaging" "simulation")

# Loop through the array
for container in ${services[*]}
do 
    # Downloading a new image
    docker pull artemkulish/demo4:$container &>/dev/null
        # If the image is updated, then, restart the container
        if docker images | awk '{print $2}' | awk 'NR==2' | grep $container &>/dev/null; then
            echo "Updating $container"
            docker-compose up --no-deps -d $container &>/dev/null &
        # If the container is already up, then, print it out
        elif 
            docker ps | grep -q $container; then
            echo "Running $container";
        # If the container is down, then, start it    
        else
            echo "Starting $container"
            docker-compose up --no-deps -d $container &>/dev/null &
        fi
done

echo "Checking active containers, please hold on..."
sleep 60
docker-compose ps

echo "Deleting unused images" 
docker image prune -a -f | grep Total
