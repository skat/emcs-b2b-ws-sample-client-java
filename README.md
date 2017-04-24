# EMCS B2B Sample Web Service Client written in Java

[![Build Status](https://travis-ci.com/skat/emcs-b2b-ws-sample-client-java.svg?token=pXpLRS1qCgHe3KVdbFyA&branch=master)](https://travis-ci.com/skat/emcs-b2b-ws-sample-client-java)

Sample client for the EMCS B2B Web Service Gateway developed in Java and using open source libraries.

**IMPORTANT NOTICE**: SKAT does not provide any kind of support for the code in this repository.

## About the client

The sample client in is implemented based on the [Apache CXF](http://cxf.apache.org/) framework,
the Spring Framework, and Java 8. See `pom.xml` file in this repo for details regarding 
the current versions of the mentioned frameworks in use.
 
The client currently implements calls to the service **OIOLedsageDocumentOpret** and the main entry
point into the source code of implementation is the class:

[OIOLedsageDocumentOpretClient.java](src/main/java/dk/skat/emcs/b2b/sample/OIOLedsageDocumentOpretClient.java)

This class constructs the request, invokes a Apache CXF generated client, and parses the response
by printing out relevant values to the log.

## Fulfillment of WS Policy of EMCS Web Services

The fulfillment of policies required to invoke EMCS B2B Web Services is configured in the file:

[emcs-policy.xml](src/main/resources/emcs-policy.xml)

Fulfillment of WS Policy requirements is achieved using CXF's in and out interceptor framework and 
the `emcs-policy.xml` file details which parts are to be signed and encrypted, and how to present 
certificate for authentication on the server side. This configuration file also demonstrates how
secure transport (https) is enabled client side.
 
## Run client

The sample client must be configured with two required parameters that are necessary for the client to run and
call the test environment of EMCS B2B Web Service Gateway. The two parameters can be obtained by contacting 
SKAT Help Desk.

The full list of parameters:

* **dk.skat.emcs.b2b.sample.P12_PASSPHRASE** (REQUIRED): Passphrase to the certificate used for authentication, signing (request), and encryption (response).
* **dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT** (REQUIRED):The endpoint of the service being invoked.
* **dk.skat.emcs.b2b.sample.TXID_PREFIX** (OPTIONAL): This parameter sets a custom prefix to the generated transaction id and is very useful when asking SKAT Help Desk to trace a particular request.

The client is then invoked as part of the **test phase** of the Maven build process that is run using the following
command line:

```sh
$ mvn clean install \
  -Ddk.skat.emcs.b2b.sample.P12_PASSPHRASE=<CHANGE_THIS> \
  -Ddk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT=<CHANGE_THIS>
  -Ddk.skat.emcs.b2b.sample.TXID_PREFIX=ACME_01_
```

The following is partial output of build and exhibits the request and response written
to the log:

**Request**:
```
Apr 12, 2017 11:07:23 AM dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpretClient invoke
INFO: 
*******************************************************************
** HovedOplysninger
**** Transaction Id: ACME_01_f57b8c74-31eb-482c-a481-966531930aea
**** Transaction Time: 2017-04-12T11:07:22.035+02:00
** VirksomhedIdentifikationStruktur
**** AfgiftOperatoerPunktAfgiftIdentifikator: DK82065873300
**** VirksomhedSENummerIdentifikator: 30808460
*******************************************************************
```

**Response**:
```
Apr 12, 2017 11:07:27 AM dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpretClient invoke
INFO: 
*******************************************************************
** HovedOplysningerSvar
**** Transaction Id: ACME_01_f57b8c74-31eb-482c-a481-966531930aea
**** Transaction Time: 2017-04-12T11:07:22.035+02:00
**** Service Identification: FS2_OIOLedsageDokumentOpret
**** Error
****** Error Code: 494
****** Error Text: Message identifier has been already used
*******************************************************************
```

In this particular output we see that the `ie815.xml` file has been sent in already.

### Modifying the IE815 file to produce an ARC Id

First ensure that the the following fields in the `ie815.xml` file are unique:

* MessageIdentifier
* LocalReferenceNumber

Then run the client again and the EMCS System will produce an ARC Identifier.

**Sample response**:
```
*******************************************************************
** HovedOplysningerSvar
**** Transaction Id: ACME_01_42166d20-65b5-4983-b7a2-9faec07abc54
**** Transaction Time: 2017-04-24T13:43:52.146+02:00
**** Service Identification: FS2_OIOLedsageDokumentOpret
Ledsagedokument Valideret Dato: 2017-04-24Z
Ledsagedokument ARC Identifikator: 17DKK1KHPMQH2W23ABI62
*******************************************************************
```

## Advanced Configuration

### Testing Expired and Revoked Certificates

The client keystore includes three certificates:

1. VOCES_gyldig.p12 with alias = `valid`.
2. VOCES_spaerret.p12 with alias = `revoked`.
3. VOCES_udloebet.p12 with alias = `expired`.

By default the client is configured to run with certificate with alias `valid`.

In order to complete a test with any of the other certificates the following files must be
changed:

* File: **src/main/resources/etc/Client_Sign.properties**

Change as described in the file itself:

```
# SKAT: Options =
# - valid (default)
# - revoked
# - expired
org.apache.ws.security.crypto.merlin.keystore.alias=revoked
```

File: **src/main/resources/emcs-policy.xml**

Change as described in the file itself:

```
<!-- SKAT: Options =
     - valid
     - expired
     - revoked
-->
<entry key="signatureUser" value="valid"/>
```

### Installing other OCESII Certificates in the client keystore

The keystore `src/main/resources/keystore/client-keystore.jks` is already prepared with the
necessary test certificate that is authorized to access the test environment. However, in the
event that another test certificate has been issued this may be installed as follows:

```
$ export P12_PASSPHRASE=CHANGE_ME
$ export P12_CURRENT_ALIAS=CHANGE_ME
$ keytool -delete -alias valid -keystore src/main/resources/keystore/client-keystore.jks -storepass storepassword
$ keytool -changealias -keystore target/VOCES_yours.p12 -storepass $P12_PASSPHRASE -alias $P12_CURRENT_ALIAS -destalias "valid"
$ keytool -v -importkeystore -srckeystore target/VOCES_yours.p12 -srcstoretype PKCS12 -destkeystore src/main/resources/keystore/client-keystore.jks -deststoretype JKS -deststorepass storepassword -srcstorepass $P12_PASSPHRASE
```

Where `P12_PASSPHRASE` and `P12_CURRENT_ALIAS` are passphrase and alias of the OCESII certificate,
respectively. The three keytool command removes the pre configured certificate, changes the the alias
of the new certificate, and finally imports it into the keystore.

## References

* [Apache CXF](http://cxf.apache.org/)
* [Apache CXF Samples](https://github.com/apache/cxf/tree/master/distribution/src/main/release/samples)
