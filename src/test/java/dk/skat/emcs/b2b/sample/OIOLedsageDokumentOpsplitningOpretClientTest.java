package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentOpsplitningOpret Test (IE825 as request)
 *
 * Purpose: Verify submit of IE825
 *
 * Test case design steps:
 *
 * Step 1: Submit IE815 (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE825 (using OIOLedsageDokumentOpsplitningOpret).
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentOpsplitningOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOpsplitningOpretClientTest.class.getName());

    @Test
    public void invoke() throws Exception {

        if (getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET) != null) {

            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();

            // Consignor
            String consignor = "DK31175143300";

            // Step 1:
            // -------
            File ie815 = new File("split-ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            String arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    consignor, ie815);

            if (arc == null) {
                LOGGER.warning("Did not receive ARC number. Exiting");
                return;
            }
            LOGGER.info("Received ARC = " + arc);
            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815.");
            sleep(2);

            // Step 2:
            // -------
            String ie825 = "split-ie825.xml";
            OIOLedsageDokumentOpsplitningOpretClient oioLedsageDokumentOpsplitningOpretClient = new OIOLedsageDokumentOpsplitningOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
            oioLedsageDokumentOpsplitningOpretClient.invoke(virksomhedSENummerIdentifikator,
                    consignor, ie825,arc);
        }
    }

}