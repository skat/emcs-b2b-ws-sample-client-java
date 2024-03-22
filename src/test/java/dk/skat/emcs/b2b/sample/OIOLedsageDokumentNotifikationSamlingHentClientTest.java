package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 1. Call OIOLedsageDokumentOpret (IE815)
 * 2. Call IOLedsageDokumentNotifikationOpret (IE819)
 *
 */
public class OIOLedsageDokumentNotifikationSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentNotifikationSamlingHentClientTest.class.getName());

    @Test
    public void invoke() throws DatatypeConfigurationException, InterruptedException, IOException, SAXException, ParserConfigurationException {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentNotifikationSamlingHent");

        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";

            String arc = null;
            // Step 1:
            // -------
            {
                File ie815 = new File("for-notification-ie815.xml");
                OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
                arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            }

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }

            LOGGER.info("Waiting 2 minutes ...");
            sleep(2);

            // Step 2:
            // -------
            {
                String ie819 = "for-notification-ie819.xml";
                OIOLedsageDokumentNotifikationOpretClient oioLedsageDokumentNotifikationOpretClient = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
                oioLedsageDokumentNotifikationOpretClient.invoke(virksomhedSENummerIdentifikator,
                        "DK99025875300", ie819, arc);
            }

            LOGGER.info("Waiting 2 minutes ...");
            sleep(2);

            // Step 3:
            // -------
            {
                OIOLedsageDokumentNotifikationSamlingHentClient client = new OIOLedsageDokumentNotifikationSamlingHentClient(endpointURL);
                client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator);
            }

        }
    }
}
