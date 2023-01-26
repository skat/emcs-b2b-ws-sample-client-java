package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentOpsplitningOpret Test
 *
 * Prerequisites: IE815 must be submitted in advance (using OIOLedsageDokumentOpret).
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentOpsplitningOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOpsplitningOpretClientTest.class.getName());

    @Test
    public void invoke() throws Exception {

        if (getEndpoint("OIOLedsageDokumentOpsplitningOpret") != null) {

            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();

            // Consignor
            String consignor = "DK31175143300";

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

            String ie825 = "split-ie825.xml";
            OIOLedsageDokumentOpsplitningOpretClient oioLedsageDokumentOpsplitningOpretClient = new OIOLedsageDokumentOpsplitningOpretClient(getEndpoint("OIOLedsageDokumentOpsplitningOpret"));
            oioLedsageDokumentOpsplitningOpretClient.invoke(virksomhedSENummerIdentifikator,
                    consignor, ie825,arc);
        }
    }

}