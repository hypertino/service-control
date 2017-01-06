#! /bin/bash
set -e

if [[ "$TRAVIS_PULL_REQUEST" == "false" && "$TRAVIS_BRANCH" == "master" ]]; then
  echo "$key_password" | gpg --passphrase-fd 0 ./travis/ht-oss-public.asc.gpg
  echo "$key_password" | gpg --passphrase-fd 0 ./travis/ht-oss-private.asc.gpg

  if grep "version\s*:=.*SNAPSHOT" build.sbt; then
    sbt 'alias testPublish =; test; publishSigned' +testPublish
  else
    sbt 'alias testPublish =; test; publishSigned' +testPublish sonatypeReleaseAll
  fi
else
  sbt 'alias testAll =; test' +testAll
fi
