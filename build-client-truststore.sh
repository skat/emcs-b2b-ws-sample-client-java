#!/bin/sh

CLIENT_TUSTSTORE=src/main/resources/keystore/client-truststore.jks

echo "Before import ..."
keytool -list -keystore $CLIENT_TUSTSTORE -storepass storepassword

echo "Importing ..."

keytool -noprompt -import -alias skatserver -file pem/emcs-test-system.pem -keystore $CLIENT_TUSTSTORE -storepass storepassword

echo "After import ..."
keytool -list -keystore $CLIENT_TUSTSTORE -storepass storepassword

### Reverse imports
### NOT ACTIVE
# keytool -delete -alias skatserver -keystore src/main/resources/keystore/client-truststore.jks -storepass storepassword
