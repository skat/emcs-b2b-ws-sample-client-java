package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOForsinkelseForklaringOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;

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
            File ie815 = new File("ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            OIOLedsageDokumentOpretOType response1 = oioLedsageDocumentClient.invoke2(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            assertFalse(hasError(response1.getHovedOplysningerSvar()));

            String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();

            assertNotNull("Did not receive ARC number. Exiting.", arc);

            sleep(2);

            // Step 2:
            // -------
            // Path to where the IE837 document is located
            String ie837 = "ie837.xml";
            OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringClient = new OIOForsinkelseForklaringOpretClient(endpointURL);
            OIOForsinkelseForklaringOpretOType response2 = oioForsinkelseForklaringClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie837, arc, afgiftOperatoerPunktAfgiftIdentifikator);
            assertFalse(hasError(response2.getHovedOplysningerSvar()));
        }
    }

}