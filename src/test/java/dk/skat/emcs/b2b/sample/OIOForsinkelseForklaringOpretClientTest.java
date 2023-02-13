package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

/**
 * OIOForsinkelseForklaringOpret Test
 * <p>
 * Purpose: Verify submit of IE837 message.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 message (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE837 message (using OIOForsinkelseForklaringOpret)
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOForsinkelseForklaringOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOForsinkelseForklaringOpretClientTest.class.getName());

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET);
        if (endpointURL != null) {

            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";

            // Step 1:
            // -------
            String arc;
            File ie815 = new File("ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }

            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815.");
            sleep(2);

            // Step 2:
            // -------
            // Path to where the IE837 document is located
            String ie837 = "ie837.xml";
            OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringClient = new OIOForsinkelseForklaringOpretClient(endpointURL);
            oioForsinkelseForklaringClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie837, arc, afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }

}