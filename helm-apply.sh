#!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash helm-apply.sh
if [ -d "csvc" ]; then
helm delete --purge csvc 2>/dev/null
helm dep up ./csvc
helm install --name csvc ./csvc --namespace default
fi
helm delete --purge revolut 2>/dev/null
helm dep up ./revolut
helm install --name revolut  ./revolut --namespace default
