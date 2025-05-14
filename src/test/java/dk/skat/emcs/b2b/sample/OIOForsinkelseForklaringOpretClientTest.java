package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOForsinkelseForklaringOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

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
    public void scenario() throws Exception {
        assumeNotNull(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));

        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";

        LOGGER.info("----- Step 1: OIOLedsageDokumentOpret");
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        OIOLedsageDokumentOpretOType response1 = oioLedsageDocumentClient.invoke2(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        assertFalse(hasError(response1.getHovedOplysningerSvar()));

        String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();

        assertNotNull("Did not receive ARC number. Exiting.", arc);

        sleep(2);

        LOGGER.info("----- Step 2: OIOForsinkelseForklaringOpret");
        // Path to where the IE837 document is located
        String ie837 = "ie837.xml";
        OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringClient = new OIOForsinkelseForklaringOpretClient(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
        OIOForsinkelseForklaringOpretOType response2 = oioForsinkelseForklaringClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie837, arc, afgiftOperatoerPunktAfgiftIdentifikator);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));
    }

}