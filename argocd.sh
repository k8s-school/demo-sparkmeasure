#!/bin/bash

# Install pre-requisite for fink ci

# @author  Fabrice Jammes

set -euxo pipefail

DIR=$(cd "$(dirname "$0")"; pwd -P)

src_dir=$DIR

export CIUXCONFIG=$HOME/.ciux/ciux.sh
# Run the CD pipeline
. $CIUXCONFIG

NS=argocd

ciux ignite --selector itest "$src_dir"

argocd login --core
kubectl config set-context --current --namespace="$NS"

APP_NAME="$CIUX_IMAGE_NAME"

argocd app create $APP_NAME --dest-server https://kubernetes.default.svc \
    --dest-namespace "$APP_NAME" \
    --repo https://github.com/k8s-school/$APP_NAME \
    --path charts/apps --revision "$DEMO_SPARKMEASURE_WORKBRANCH" \
    -p spec.source.targetRevision.default="$DEMO_SPARKMEASURE_WORKBRANCH"

argocd app sync $APP_NAME

argocd app set spark-demo -p image.tag="$CIUX_IMAGE_TAG"

argocd app sync -l app.kubernetes.io/part-of=$APP_NAME,app.kubernetes.io/component=operator
argocd app wait -l app.kubernetes.io/part-of=$APP_NAME,app.kubernetes.io/component=operator

argocd app sync -l app.kubernetes.io/part-of=$APP_NAME


