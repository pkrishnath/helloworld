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


Infrastructure

## Preparation

You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:

```
$ docker push revolut
```

## Prerequisites

`helm cli and tiller` should be installed. Instructions on installing `helm` can be referred at [https://github.com/helm/helm](https://github.com/helm/helm)

Once, helm is installed, then you need to add the below repositories,
```
helm repo add stable https://kubernetes-charts.storage.googleapis.com
helm repo add incubator https://kubernetes-charts-incubator.storage.googleapis.com
```
These repos should be added to the local cache, because this sub-generator will pull some charts from these repos.

This repo in turn uses `kubernets` sub-generator for the microservices manifests and few service like db, elk, prometheus etc. are referred from the above repos.


## Deployment

You can deploy all your apps by running the below bash command:

```
bash helm-apply.sh (or) ./helm-apply.sh
```

`helm-apply.sh` will always do a clean install. Any of the existing charts with the same identity gets removed first and then it does a clean install.


You can upgrade all your apps (if at all if you have made any changes to the generated manifests) by running the below bash command:

```
bash helm-upgrade.sh (or) ./helm-upgrade.sh
```

