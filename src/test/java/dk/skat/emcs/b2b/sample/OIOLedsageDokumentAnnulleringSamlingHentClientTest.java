package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentAnnulleringSamlingHent Test
 *
 * Purpose: Verify retrieval of IE810 messages.
 *
 * Test case design steps:
 *
 * Step 1: Submit IE815 (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE810 (using OIOLedsageDokumentAnnulleringOpret)
 * Step 3: Get IE810 documents
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentAnnulleringSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentAnnulleringSamlingHentClientTest.class.getName());

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException, InterruptedException {

        if (getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT) != null) {

            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            // Step 1:
            // -------
            File ie815 = new File("ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            String arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }

            LOGGER.info("Received ARC = " + arc);
            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815.");
            sleep(2);

            // Step 2:
            // -------
            OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, "ie810.xml", arc);

            LOGGER.info("Sleeping 1 minute to allow EMCS process the IE810.");
            sleep(1);

            // Step 3:
            // -------
            OIOLedsageDokumentAnnulleringSamlingHentClient client = new OIOLedsageDokumentAnnulleringSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, 1);
        }
    }
}