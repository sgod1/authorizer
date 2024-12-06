#!/bin/bash -x

echo "classpath=${classpath}" > ./classpath.env

mvn dependency:build-classpath >> ./classpath.env
