#!/bin/bash


# Create docker image containing hadoop for fink-broker on k8s

# @author  Fabrice Jammes

set -euxo pipefail

DIR=$(cd "$(dirname "$0")"; pwd -P)

. $DIR/conf.sh

usage() {
  cat << EOD

Usage: `basename $0` [options]

  Available options:
    -h          this message

Build image containing hadoop for fink-broker on k8s
EOD
}

# get the options
while getopts h c ; do
    case $c in
	    h) usage ; exit 0 ;;
	    \?) usage ; exit 2 ;;
    esac
done
shift `expr $OPTIND - 1`

cd $DIR/spark-jmx
sbt package
cd $DIR
cp $DIR/spark-jmx/target/scala-2.12/*.jar $DIR/rootfs/opt/spark/jars/

# Check if changes in $DIR/rootfs/opt/spark/jars/
if [ -z "$(git -C $DIR status --porcelain rootfs/opt/spark/jars/)" ]; then
  echo "No changes in spark-jmx jar, skipping commit"
  exit 0
else
  echo "Changes detected in spark-jmx jar, proceeding with commit"
  git -C $DIR add rootfs
  git commit -m "Update spark-jmx jar"
  git push
fi

# This command avoid retrieving build dependencies if not needed
$(ciux get image --check $DIR --env)

if [ $CIUX_BUILD = false ];
then
    echo "Build cancelled, image $CIUX_IMAGE_URL already exists and contains current source code"
    exit 0
fi

ciux ignite --selector build $DIR
. $CIUXCONFIG

# Build image
docker image build --tag "$CIUX_IMAGE_URL" "$DIR"

echo "Build successful"

