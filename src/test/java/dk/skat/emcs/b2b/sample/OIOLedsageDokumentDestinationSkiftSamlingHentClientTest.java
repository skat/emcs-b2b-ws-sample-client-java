package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentDestinationSkiftSamlingHent Test
 * <p>
 * Purpose: Verify retrieval of IE813 messages.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 message (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE813 message (using OIOLedsageDokumentDestinationSkiftOpret)
 * Step 3: Get IE813 message(s) (using OIOLedsageDokumentDestinationSkiftSamlingHent)
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentDestinationSkiftSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentDestinationSkiftSamlingHentClientTest.class.getName());

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException, InterruptedException {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentDestinationSkiftSamlingHent");

        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";

            String arc;
            // Step 1:
            // -------
            {
                File ie815 = new File("change-dest-ie815.xml");
                OIOLedsageDokumentOpretClient client = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
                arc = client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            }

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }

            LOGGER.info("Received ARC = " + arc);
            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815 message.");

            sleep(2);

            // Step 2:
            // -------
            {
                String ie813 = "change-dest-ie813.xml";
                OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint("OIOLedsageDokumentDestinationSkiftOpret"));
                client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
            }

            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE810 message.");
            sleep(2);

            // Step 3:
            // -------
            {
                OIOLedsageDokumentDestinationSkiftSamlingHentClient client = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(endpointURL);
                client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator);

            }

        }

    }

}