#!/bin/bash -x

source ./classpath.env

profile=$1

authorizer_profile="authorizer"
issuer_profile="issuer"
rules_profile="rules"
risk_profile="risk"
local_profile="local"

if test -z $profile; then
  echo profile argument required: $authorizer_profile, $issuer_profile, $rules_profile, $risk_profile, $local_profile
  exit 1
fi

authorizer_port=${2:-"8080"}
issuer_port=${3:-"8081"}
rules_port=${4:-"8082"}
risk_port=${5:-"8083"}

this_host=`hostname -f`

issuer_host=${6:-$this_host}
rules_host=${7:-$this_host}
risk_host=${8:-$this_host}

server_port=""
jvm_name=""
application_name=""
if test "${profile}" = "${authorizer_profile}"; then
  server_port=$authorizer_port
  jvm_name="AuthorizerJVM"
  application_name="AuthorizerSpringApp"

elif test "${profile}" = "${issuer_profile}"; then
  server_port=$issuer_port
  jvm_name="IssuerJVM"
  application_name="IssuerSpringApp"

elif test "${profile}" = "${rules_profile}"; then
  server_port=$rules_port
  jvm_name="RulesJVM"
  application_name="RulesSpringApp"

elif test "${profile}" = "${risk_profile}"; then
  server_port=$risk_port
  jvm_name="RiskJVM"
  application_name="RiskSpringApp"

elif test "${profile}" = "${local_profile}"; then
  jvm_name="AllProfilesAuthorizerJVM"
  application_name="AllProfilesAuthorizerSpringApp"

  server_port=$authorizer_port
  issuer_port=$authorizer_port
  rules_port=$authorizer_port
  risk_port=$authorizer_port

  issuer_host=$this_host
  rules_host=$this_host
  risk_host=$this_host

else
  echo invalid profile argument, one of: $authorizer_profile, $issuer_profile, $rules_profile, $risk_profile, $local_profile
  exit 1
fi

export SPRING_APPLICATION_NAME=$application_name

jvmargs="-XX:+EnableDynamicAgentLoading -Dcom.instana.agent.jvm.name=$jvm_name"
springargs="--spring.profiles.active=$profile --server.port=$server_port --spring.application.name=$application_name"
applargs1="--authorizer.issuer-processor-host=$issuer_host --authorizer.rules-processor-host=$rules_host --authorizer.risk-processor-host=$risk_host"
applargs2="--authorizer.issuer-processor-port=$issuer_port --authorizer.rules-processor-port=$rules_port --authorizer.risk-processor-port=$risk_port"

version="0.0.1-SNAPSHOT"

java --version

#java -jar target/authorizer-$version.jar -cp $classpath $jvmargs $springargs $applargs1 $applargs2

java -cp $classpath:/home/simon/authorizer/src/authorizer/target-${profile}/classes $jvmargs io.issuer.authorizer.AuthorizerApplication $springargs $applargs1 $applargs2
