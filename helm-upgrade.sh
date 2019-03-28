#!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash helm-apply.sh
if [ -d "csvc" ]; then
helm dep up ./csvc
helm upgrade --install csvc ./csvc --namespace default
fi
helm dep up ./revolut
helm upgrade --install revolut ./revolut --namespace default
