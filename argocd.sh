#!/bin/bash

# Install pre-requisite for fink ci

# @author  Fabrice Jammes

set -euxo pipefail

DIR=$(cd "$(dirname "$0")"; pwd -P)

export CIUXCONFIG=$HOME/.ciux/ciux.sh
# Run the CD pipeline
. $CIUXCONFIG

NS=argocd

argocd login --core
kubectl config set-context --current --namespace="$NS"

APP_NAME="$CIUX_IMAGE_NAME"

argocd app create $APP_NAME --dest-server https://kubernetes.default.svc \
    --dest-namespace "$APP_NAME" \
    --repo https://github.com/k8s-school/$APP_NAME \
    --path apps --revision "$DEMO_SPARKMEASURE_WORKBRANCH"


argocd app sync $APP_NAME
