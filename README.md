# EMCS B2B Sample Web Service Client written in Java

![JDK25](https://img.shields.io/badge/Java-25-green.svg)

Sample clients for the EMCS B2B Web Service Gateway developed in Java and using open source libraries.

**IMPORTANT NOTICE**: SKAT does not provide any kind of support for the code in this repository.
This Java-client is just one example of how a B2B web service can be accessed. The client must not be 
perceived as a piece of production code but more as an example one can take inspiration from and can use
to quickly get started to test whether your company can implement a successful call to one of the B2B web 
service using the company's digital signature. SKAT can not be held responsible if a company uses this client
or parts of it in their own systems. 

**VIGTIG MEDDELELSE**: SKAT yder ikke support på kildekoden i nærværende kodebibliotek.
Denne Java-klient er kun et eksempel på hvordan B2B webservicene kan tilgås. Klienten skal således ikke 
opfattes som et stykke produktionskode men mere som en eksempel man kan lade sig inspirere af og kan bruge 
til hurtigt at komme i gang og få afprøvet om ens virksomhed kan gennemføre et succesfuldt kald til en af 
B2B webservicene ved at bruge virksomhedens digitale signatur. SKAT kan ikke stå til ansvar hvis en virksomhed
anvender klienten eller dele af denne i deres egne systemer. 

## About the client

The sample clients are implemented based on the [Apache CXF](http://cxf.apache.org/) framework,
the Spring Framework, and Java 25. See `pom.xml` file in this repo for details regarding 
the current versions of the mentioned frameworks in use.
 
The sample clients currently implements calls to the services:
 
* **OIOLedsageDokumentOpret** : Submit IE815 document
* **OIOLedsageDokumentSamlingHent** : Fetch IE801 document related to ARC number
* **OIOBeskedAfvisningSamlingHent** : Search for IE704 documents within the last month

Service **OIOLedsageDocumentOpret** must invoked before invoking **OIOLedsageDokumentSamlingHent** (using the
returned ARC number) and **OIOBeskedAfvisningSamlingHent** (if submitted IE815 was not processed by **OIOLedsageDocumentOpret**).

The main entry point into the source code of the implementation is these classes:

* [OIOLedsageDokumentOpretClient.java](src/main/java/dk/skat/emcs/b2b/sample/OIOLedsageDokumentOpretClient.java)
* [OIOLedsageDokumentSamlingHentClient.java](src/main/java/dk/skat/emcs/b2b/sample/OIOLedsageDokumentSamlingHentClient.java)
* [OIOBeskedAfvisningSamlingHentClient.java](src/main/java/dk/skat/emcs/b2b/sample/OIOBeskedAfvisningSamlingHentClient.java)

These classes construct the request, invokes a Apache CXF generated client, and parses the response
by printing out relevant values to the log.

## Fulfillment of WS Policy of EMCS Web Services

The fulfillment of policies required to invoke EMCS B2B Web Services is configured in the file:

[emcs-policy.xml](src/main/resources/emcs-policy.xml)

Fulfillment of WS Policy requirements is achieved using CXF's in and out interceptor framework and 
the `emcs-policy.xml` file details which parts are to be signed and encrypted, and how to present 
certificate for authentication on the server side. This configuration file also demonstrates how
secure transport (https) is enabled client side.
 
## Run clients

The sample clients must be configured with a combination of a few JVM parameters and a config file named `app.conf` for 
the client to run and call the test environment of EMCS B2B Web Service Gateway. 
The parameters and the `app.conf` file can be obtained by contacting SKAT Help Desk.

The client is then invoked as part of the **test phase** of the Maven build process using the following
command:

```sh
$ mvn clean install \
  -Dtest="OIOLedsageDokumentOpretClientTest#invoke" \
  -Ddk.skat.emcs.b2b.sample.ClientCertAlias='<CHANGE_THIS>' \
  -DskipTests=false
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

## Fetch IE801 documents using OIOLedsageDokumentSamlingHent

The service **OIOLedsageDokumentSamlingHent** returns IE801 documents and in the following
sample we call this service using as search input the ARC number received in the sample response 
above (`Ledsagedokument ARC Identifikator: 17DKK1KHPMQH2W23ABI62`).

The client for **OIOLedsageDokumentSamlingHent** is invoked as part of the **test phase** of the Maven 
build process using the following command:


```sh
$ mvn clean install \
  -Dtest="OIOLedsageDokumentSamlingHentClientTest#invoke" \
  -Ddk.skat.emcs.b2b.sample.ARC=17DKK1KHPMQH2W23ABI62 \
  -Ddk.skat.emcs.b2b.sample.ClientCertAlias='<CHANGE_THIS>' \
  -DskipTests=false
```

This service returns a **response** similar to this output (in thie case PHASE 3 based document):

```
*******************************************************************
** HovedOplysningerSvar
**** Transaction Id: ACME_01_7586f935-6997-47d1-83a0-f70ec7a64d8f
**** Transaction Time: 2017-11-20T12:24:39.425+01:00
**** Service Identification: FS2_OIOLedsageDokumentSamlingHent
*******************************************************************
** IE 801 Messages: 
<IE801 xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
       xmlns:client="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE801:V1.76"
       xmlns="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE801:V1.76">
    <ns25:Header xmlns:ns25="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE801:V1.76">
        <ns19:MessageSender xmlns:ns19="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.76">NDEA.DK</ns19:MessageSender>
        <ns19:MessageRecipient xmlns:ns19="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.76">NDEA.DK
        </ns19:MessageRecipient>
        <ns19:DateOfPreparation xmlns:ns19="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.76">2011-10-26
        </ns19:DateOfPreparation>
        <ns19:TimeOfPreparation xmlns:ns19="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.76">11:23:00.803
        </ns19:TimeOfPreparation>
        <ns19:MessageIdentifier xmlns:ns19="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.76">DKEMCS20170000000287316
        </ns19:MessageIdentifier>
    </ns25:Header>
    <ns25:Body xmlns:ns25="urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE801:V1.76">
    ...
    ...
    ...
    ...
    </ns25:Body>
</IE801>
*******************************************************************
```

*NOTE*: The XML above is pretty printed and the body part removed (to avoid filling up the whole README file).

## Fetch IE704 documents using OIOBeskedAfvisningSamlingHent

The client for **OIOBeskedAfvisningSamlingHent** is invoked as part of the **test phase** of the Maven 
build process using the following command:

```sh
$ mvn clean install \
  -Dtest="OIOBeskedAfvisningSamlingHentClientTest" \
  -Ddk.skat.emcs.b2b.sample.ClientCertAlias='<CHANGE_THIS>' \
  -DskipTests=false
```

The client is configured to search for documents within the last month. If there are no 704 messages found,  
the service returns a **response** similar to this output:

```
*******************************************************************
** HovedOplysningerSvar
**** Transaction Id: ACME_01_9c0848aa-366f-4765-9e1b-ddfbdb86aed1
**** Transaction Time: 2017-11-20T13:23:12.630+01:00
**** Service Identification: FS2_OIOBeskedAfvisningSamlingHent
*******************************************************************
**** Advis
****** Advis Code: 130
****** Advis Text: Der blev ikke fundet nogen beskeder som matcher de indikeret søgeparametre
```

## Testing with valid, expired, and revoked Certificates

The client keystore includes 6 certificates that will allow testing different scenarios.

<table>
<thead>
  <tr>
    <th>Version</th>
    <th>Alias</th>
    <th>Scenario</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>OCES3</td>
    <td>dinovinoimport_system_integrationstest_s1</td>
    <td>Submit and fetch documents</td>
  </tr>
  <tr>
    <td>OCES3</td>
    <td>dinovinoimport_organisation_integrationstest_o1</td>
    <td>Cert. is registered, but calls denied due to missing authorization</td>
  </tr>
  <tr>
    <td>OCES3</td>
    <td>peter_punktafgift</td>
    <td>Calls will be denied due to cert. NOT registered</td>
  </tr>

  <tr>
    <td>OCES2</td>
    <td>valid</td>
    <td>Submit and fetch documents</td>
  </tr>
  <tr>
    <td>OCES2</td>
    <td>revoked</td>
    <td>Call denied due to revoked certificate</td>
  </tr>
  <tr>
    <td>OCES2</td>
    <td>expired</td>
    <td>Call denied due to expired certificate</td>
  </tr>
</tbody>
</table>

### Installing other Certificates in the client keystore

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
respectively. The three keytool command removes the pre configured certificate, changes the alias
of the new certificate, and finally imports it into the keystore.

## References

* [Apache CXF](http://cxf.apache.org/)
* [Apache CXF Samples](https://github.com/apache/cxf/tree/master/distribution/src/main/release/samples)
