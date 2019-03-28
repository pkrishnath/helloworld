# Revolut Hello World

## Development

To start your application in the dev profile, simply run:

   ./gradlew

`dev` profile uses h2 (In memory Database) as datastore

To make sure , app is running locally as expected, run

```
curl -X PUT http://localhost:8081/hello/krish -d '{"dateOfBirth":"1984-06-19"}'

curl -X GET http://localhost:8081/hello/krish -H 'Content-Type: application/json'
```

## Building for production

To optimize the `revolut hello world application` for production, run:

    ./gradlew -Pprod clean bootWar

`prod` profile uses mysql as data store


## Testing

To launch your application's tests, run:

    ./gradlew test

## Build  docker image using Google's jib

This project uses Google's jib to build docker image

To build a docker image of your app, run:

    ./gradlew bootWar -Pprod jibDockerBuild

## Using Docker Compose to run application on local environment

To start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To start your app, run:

    docker-compose -f src/main/docker/app.yml up -d

To make sure , app is running locally as expected, run

```
curl -X PUT http://localhost:8081/hello/krish -d '{"dateOfBirth":"1984-06-19"}'

curl -X GET http://localhost:8081/hello/krish -H 'Content-Type: application/json'
```


# Infrastructure

The serveice will be deplyed on Amazon EKS to incorporating scalability, stability, monitoring and disaster recovery.

![EKS](images/EKS.png?raw=true "Infrastructure")

## Prerequisites

You need pre-provisioned EKS/GKE cluster

`helm cli and tiller` should be installed. Instructions on installing `helm` can be referred at [https://github.com/helm/helm](https://github.com/helm/helm)


## Preparation

You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:

```
$ docker tag revolut-helloworld <dockerhub_username>/revolut-helloworld
$ docker push <dockerhub_username>/revolut-helloworld
```
NB: the latest images has been pushed to pkrishnath/revolut-helloworld

## Deployment

You can deploy all your apps by running the below bash command:

```
bash helm-apply.sh (or) ./helm-apply.sh
```

`helm-apply.sh` will always do a clean install. Any of the existing charts with the same identity gets removed first and then it does a clean install.


You can upgrade all your apps  by running the below bash command:

```
bash helm-upgrade.sh (or) ./helm-upgrade.sh
```

## Accessing app from kubernetes cluster

TODO: Ingress is not implemented

You can use `kubectl port-forward` command to access your application from localhost

eg: `kubectl port-forward service/revolut-helloworld-helloworld-revolut 8081:8081` and application can be access via localhost:8081






