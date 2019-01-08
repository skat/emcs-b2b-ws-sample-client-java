package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;

/**
 * OIOLedsageDocumentOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDocumentOpretClientStringTest extends BaseClient {

  //  @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT");
        if (endpointURL != null) {

            // Path to where the IE815 document is located
            String ie815 = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<ie:IE815 xmlns:ie=\"urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE815:V1.92\">\n" +
                    "    <ie:Header xmlns:tms=\"urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:TMS:V1.92\">\n" +
                    "        <tms:MessageSender>NDEA.DK</tms:MessageSender>\n" +
                    "        <tms:MessageRecipient>NDEA.DK</tms:MessageRecipient>\n" +
                    "        <tms:DateOfPreparation>2011-10-26</tms:DateOfPreparation>\n" +
                    "        <tms:TimeOfPreparation>11:27:00.803</tms:TimeOfPreparation>\n" +
                    "        <tms:MessageIdentifier>9e1e74a5-aaae-41d6-8280-c3892246e783</tms:MessageIdentifier>\n" +
                    "    </ie:Header>\n" +
                    "    <ns26:Body xmlns:ns26=\"urn:publicid:-:EC:DGTAXUD:EMCS:PHASE3:IE815:V1.92\">\n" +
                    "        <ns26:SubmittedDraftOfEAD>\n" +
                    "            <ns26:Attributes>\n" +
                    "                <ns26:SubmissionMessageType>1</ns26:SubmissionMessageType>\n" +
                    "                <!-- IMPORTANT: We ONLY set this to 1 to allows us to resend\n" +
                    "                 the same document with DateOfDispatch date in the past -->\n" +
                    "                <ns26:DeferredSubmissionFlag>1</ns26:DeferredSubmissionFlag>\n" +
                    "            </ns26:Attributes>\n" +
                    "            <ns26:ConsigneeTrader language=\"da\">\n" +
                    "                <ns26:Traderid>DK99025875300</ns26:Traderid>\n" +
                    "                <ns26:TraderName>SEED selskab 1 test 2</ns26:TraderName>\n" +
                    "                <ns26:StreetName>Kirkegade</ns26:StreetName>\n" +
                    "                <ns26:StreetNumber>1</ns26:StreetNumber>\n" +
                    "                <ns26:Postcode>6840</ns26:Postcode>\n" +
                    "                <ns26:City>Oksbøl</ns26:City>\n" +
                    "            </ns26:ConsigneeTrader>\n" +
                    "            <ns26:ConsignorTrader language=\"da\">\n" +
                    "                <ns26:TraderExciseNumber>DK82065873300</ns26:TraderExciseNumber>\n" +
                    "                <ns26:TraderName>Test af KS-1, testsitnr. 3.1.3.22</ns26:TraderName>\n" +
                    "                <ns26:StreetName>Borupvej</ns26:StreetName>\n" +
                    "                <ns26:StreetNumber>1</ns26:StreetNumber>\n" +
                    "                <ns26:Postcode>3320</ns26:Postcode>\n" +
                    "                <ns26:City>Skævinge</ns26:City>\n" +
                    "            </ns26:ConsignorTrader>\n" +
                    "            <ns26:PlaceOfDispatchTrader language=\"da\">\n" +
                    "                <ns26:ReferenceOfTaxWarehouse>DK82065873309</ns26:ReferenceOfTaxWarehouse>\n" +
                    "                <ns26:TraderName>Test af KS-1, testsitnr. 3.1.3.22</ns26:TraderName>\n" +
                    "                <ns26:StreetName>Borupvej</ns26:StreetName>\n" +
                    "                <ns26:StreetNumber>9</ns26:StreetNumber>\n" +
                    "                <ns26:Postcode>3320</ns26:Postcode>\n" +
                    "                <ns26:City>Skævinge</ns26:City>\n" +
                    "            </ns26:PlaceOfDispatchTrader>\n" +
                    "            <ns26:DeliveryPlaceTrader language=\"da\">\n" +
                    "                <ns26:Traderid>DK99025875499</ns26:Traderid>\n" +
                    "                <ns26:TraderName>SEED selskab 1 test 2</ns26:TraderName>\n" +
                    "                <ns26:StreetName>Statshavnen</ns26:StreetName>\n" +
                    "                <ns26:StreetNumber>6</ns26:StreetNumber>\n" +
                    "                <ns26:Postcode>3000</ns26:Postcode>\n" +
                    "                <ns26:City>Helsingør</ns26:City>\n" +
                    "            </ns26:DeliveryPlaceTrader>\n" +
                    "            <ns26:CompetentAuthorityDispatchOffice>\n" +
                    "                <ns26:ReferenceNumber>DK008047</ns26:ReferenceNumber>\n" +
                    "            </ns26:CompetentAuthorityDispatchOffice>\n" +
                    "            <ns26:FirstTransporterTrader language=\"da\">\n" +
                    "                <ns26:TraderName>TC10</ns26:TraderName>\n" +
                    "                <ns26:StreetName>Lufthavnsvej</ns26:StreetName>\n" +
                    "                <ns26:StreetNumber>8</ns26:StreetNumber>\n" +
                    "                <ns26:Postcode>2800</ns26:Postcode>\n" +
                    "                <ns26:City>Roskilde</ns26:City>\n" +
                    "            </ns26:FirstTransporterTrader>\n" +
                    "            <ns26:HeaderEad>\n" +
                    "                <ns26:DestinationTypeCode>1</ns26:DestinationTypeCode>\n" +
                    "                <ns26:JourneyTime>H06</ns26:JourneyTime>\n" +
                    "                <ns26:TransportArrangement>1</ns26:TransportArrangement>\n" +
                    "            </ns26:HeaderEad>\n" +
                    "            <ns26:TransportMode>\n" +
                    "                <ns26:TransportModeCode>4</ns26:TransportModeCode>\n" +
                    "            </ns26:TransportMode>\n" +
                    "            <ns26:MovementGuarantee>\n" +
                    "                <ns26:GuarantorTypeCode>1</ns26:GuarantorTypeCode>\n" +
                    "            </ns26:MovementGuarantee>\n" +
                    "            <ns26:BodyEad>\n" +
                    "                <ns26:BodyRecordUniqueReference>1</ns26:BodyRecordUniqueReference>\n" +
                    "                <ns26:ExciseProductCode>W200</ns26:ExciseProductCode>\n" +
                    "                <ns26:CnCode>22042122</ns26:CnCode>\n" +
                    "                <ns26:Quantity>100</ns26:Quantity>\n" +
                    "                <ns26:GrossWeight>100</ns26:GrossWeight>\n" +
                    "                <ns26:NetWeight>99</ns26:NetWeight>\n" +
                    "                <ns26:AlcoholicStrength>12</ns26:AlcoholicStrength>\n" +
                    "                <ns26:FiscalMark language=\"da\">Nix</ns26:FiscalMark>\n" +
                    "                <ns26:FiscalMarkUsedFlag>1</ns26:FiscalMarkUsedFlag>\n" +
                    "                <ns26:DesignationOfOrigin language=\"da\">Tjo</ns26:DesignationOfOrigin>\n" +
                    "                <ns26:SizeOfProducer>4000000</ns26:SizeOfProducer>\n" +
                    "                <ns26:CommercialDescription language=\"da\">Nix</ns26:CommercialDescription>\n" +
                    "                <ns26:BrandNameOfProducts language=\"da\">BB</ns26:BrandNameOfProducts>\n" +
                    "                <ns26:Package>\n" +
                    "                    <ns26:KindOfPackages>BJ</ns26:KindOfPackages>\n" +
                    "                    <ns26:NumberOfPackages>10</ns26:NumberOfPackages>\n" +
                    "                </ns26:Package>\n" +
                    "                <ns26:WineProduct>\n" +
                    "                    <ns26:WineProductCategory>2</ns26:WineProductCategory>\n" +
                    "                    <ns26:WineGrowingZoneCode>1</ns26:WineGrowingZoneCode>\n" +
                    "                    <ns26:OtherInformation language=\"da\">jajaja</ns26:OtherInformation>\n" +
                    "                </ns26:WineProduct>\n" +
                    "            </ns26:BodyEad>\n" +
                    "            <ns26:EadDraft>\n" +
                    "                <ns26:LocalReferenceNumber>1793461</ns26:LocalReferenceNumber>\n" +
                    "                <ns26:InvoiceNumber>INV026594</ns26:InvoiceNumber>\n" +
                    "                <ns26:InvoiceDate>2011-10-18</ns26:InvoiceDate>\n" +
                    "                <ns26:OriginTypeCode>1</ns26:OriginTypeCode>\n" +
                    "                <ns26:DateOfDispatch>2011-10-26</ns26:DateOfDispatch>\n" +
                    "                <ns26:TimeOfDispatch>02:00:00.814</ns26:TimeOfDispatch>\n" +
                    "            </ns26:EadDraft>\n" +
                    "            <ns26:TransportDetails>\n" +
                    "                <ns26:TransportUnitCode>1</ns26:TransportUnitCode>\n" +
                    "                <ns26:IdentityOfTransportUnits>299</ns26:IdentityOfTransportUnits>\n" +
                    "            </ns26:TransportDetails>\n" +
                    "        </ns26:SubmittedDraftOfEAD>\n" +
                    "    </ns26:Body>\n" +
                    "</ie:IE815>\n");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDocumentOpretClient oioLedsageDocumentClient = new OIOLedsageDocumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815,true);
        } else {
            System.out.println("OIOLedsageDocumentOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}