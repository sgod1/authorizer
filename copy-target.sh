#!/bin/bash -x

mvn clean package -Dmaven.test.skip=true

mv ./target/authorizer-0.0.1-SNAPSHOT.jar.original ./target/authorizer-0.0.1-SNAPSHOT.jar

cp ./truststore.p12 ./target/classes

rm -r ./target-authorizer
rm -r ./target-issuer
rm -r ./target-rules
rm -r ./target-risk

cp -r ./target ./target-authorizer
cp -r ./target ./target-issuer
cp -r ./target ./target-rules
cp -r ./target ./target-risk

