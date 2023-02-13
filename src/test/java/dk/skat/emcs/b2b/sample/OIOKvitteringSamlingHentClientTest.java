package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.util.logging.Logger;

public class OIOKvitteringSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOKvitteringSamlingHentClientTest.class.getName());


    @Test
    public void invoke() throws DatatypeConfigurationException {
        String endpointURL =
                getEndpoint("OIOKvitteringSamlingHent");
        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, null);
        }
    }

    @Test
    public void testScenario() throws Exception {
        if (getEndpoint(OIO_KVITTERING_SAMLIMG_HENT) != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String consignor = getAfgiftOperatoerPunktAfgiftIdentifikator();

            String arc;

            String consignee = "DK99025875300";

            // Call OIOLedsageDokumentOpret as Consignor

            File ie815 = new File("ie815.xml");
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

            File ie818 = new File ("ie818.xml");
            OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
            oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    consignee,ie818, arc);

            LOGGER.info("Waiting 2 minutes before proceeding...");
            sleep(2);

            // Call OIOKvitteringSamlingHent as Consignee

            OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
            client.invoke(virksomhedSENummerIdentifikator,
                    consignee, arc);
        }
    }
}