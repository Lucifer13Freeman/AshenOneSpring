#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
      target/AshenOneSpring-1.0-SNAPSHOT.jar \
      lucifer@192.168.0.3:/home/lucifer

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa lucifer@192.168.0.3 << EOF

pgrep java | xargs kill -9
nohup java -jar AshenOneSpring-1.0-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye, samurai!'