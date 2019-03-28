#!/bin/bash
helm dep up ./charts/helloworld
helm upgrade --install revolut-helloworld ./charts/helloworld --namespace default
