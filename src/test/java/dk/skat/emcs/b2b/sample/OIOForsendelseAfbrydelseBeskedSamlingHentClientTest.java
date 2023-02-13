package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * OIOForsendelseAfbrydelseBeskedSamlingHent Test (IE807 as response)
 *
 * IMPORTANT: This test case can first be run following steps completed
 * in the test system by the Danish Customs and Tax Administration.
 *
 * Contact help desk and request test data for the service: OIOForsendelseAfbrydelseBeskedSamlingHent
 *
 */
public class OIOForsendelseAfbrydelseBeskedSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOForsendelseAfbrydelseBeskedSamlingHentClientTest.class.getName());

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {

        if (getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT) != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
            OIOForsendelseAfbrydelseBeskedSamlingHentClient client = new OIOForsendelseAfbrydelseBeskedSamlingHentClient(getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT));
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }

    @Test
    public void testCreateIE815andIE818andIE871() throws Exception {
        String endpointURL =
                getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT);

        if (endpointURL != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String consignor = "DK31175143300";

            String arc = null;

            String consignee = "DK99025875300";

            // Call OIOLedsageDokumentOpret as Consignor

            File ie815 = new File("reject-ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    consignor, ie815);

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }
            LOGGER.info("Received ARC = " + arc);

            LOGGER.info("Waiting 2 minutes before proceeding...");
            sleep(2);

            // Call OIOLedsageDokumentSamlingHent as Consignee
            OIOLedsageDokumentSamlingHentClient ledsageDokumentSamlingHentClient = new OIOLedsageDokumentSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
            ledsageDokumentSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    consignee, arc);

            // Call OIOKvitteringOpret as Consignee

            File ie818 = new File ("reject-ie818.xml");
            OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
            oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    consignee,ie818, arc);

            LOGGER.info("Waiting 2 minutes before proceeding...");
            sleep(2);

            String ie871 = "reject-ie871.xml";
            OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(getEndpoint("OIOKvitteringAfvigelseBegrundelseOpret"));
            oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                    consignee, ie871, arc);



        }
    }
}