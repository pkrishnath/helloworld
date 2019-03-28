#!/bin/bash
helm delete --purge revolut 2>/dev/null
helm dep up ./charts/helloworld
helm install  --name  revolut-helloworld ./charts/helloworld  --namespace default
