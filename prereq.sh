#!/bin/bash

# Install pre-requisite for fink ci

# @author  Fabrice Jammes

set -euxo pipefail

DIR=$(cd "$(dirname "$0")"; pwd -P)

ciux_version=v0.0.4-rc10
export CIUXCONFIG=$HOME/.ciux/ciux.sh
go install github.com/k8s-school/ciux@"$ciux_version"

echo "Ignite the project using ciux"
mkdir -p ~/.ciux
ciux ignite --selector itest $DIR

cluster_name=$(ciux get clustername $DIR)
monitoring=false

# Get kind version from option -k
while getopts mk: flag
do
    case "${flag}" in
        k) kind_version_opt=--kind-version=${OPTARG};;
        m) monitoring=true;;
    esac
done

ktbx install kind
ktbx install kubectl
ktbx install helm
ink "Create kind cluster"
ktbx create -s --name $cluster_name
ink "Install OLM"
ktbx install olm
ink "Install ArgoCD operator"
ktbx install argocd
