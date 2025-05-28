# demo-sparkmeasure

Integrate sparkMeasure for Enhanced Performance Monitoring

# How to run the demo

## Pre-requisites

- sudo access
- golang 1.23.4+ (set path: `export PATH=$(go env GOPATH)/bin:/usr/local/go/bin:$PATH`)
- docker 26.1.3+

## Run the full demo

It will install a local Kubernetes cluster running:

- argocd
- prometheus
- spark-operator
- a spark job using spark-measure

```
git clone https://github.com/k8s-school/demo-sparkmeasure.git
cd demo-sparkmeasure
# Install and run everything from scratch
run-all.sh
# Access the spark pods
kubectl get pods -n spark
# Dump the metrics from the prometheus exporter of the spark job
./check-metrics.sh
```

> ⚠️ **Warning**
>
> Occasionally, the job may fail due to transient issues while downloading Maven dependencies (e.g., network instability or remote repository timeouts).
> In such cases, simply re-running the `argocd.sh` script typically resolves the problem:
>
> ```bash
> ./argocd.sh
> ```
>
> If failures persist, verify network connectivity, proxy settings (if any), and the availability of the Maven repositories.
