package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpsplitningOpretOType;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentOpsplitningOpret Test (IE825 as request)
 * <p>
 * Purpose: Verify submit of IE825
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE825 (using OIOLedsageDokumentOpsplitningOpret).
 * Step 3: Fetch IE803(s) (using OIOLedsageDokumentOmdirigeretAdvisSamlingHent) using date range as search param
 * Step 4: Fetch IE803 (using OIOLedsageDokumentOmdirigeretAdvisSamlingHent) using ARC as search param
 * <p>
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentOpsplitningOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOpsplitningOpretClientTest.class.getName());

    @Test
    public void scenario() throws Exception {
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));

        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();

        // Consignor
        String consignor = "DK31175143300";

        LOGGER.info("----- Step 1: OIOLedsageDokumentOpret");
        File ie815 = new File("split-ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        OIOLedsageDokumentOpretOType response1 = oioLedsageDocumentClient.invoke2(virksomhedSENummerIdentifikator,
                consignor, ie815);
        assertFalse(hasError(response1.getHovedOplysningerSvar()));

        String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();
        assertNotNull("Did not receive ARC number. Exiting.", arc);

        LOGGER.info("Received ARC = " + arc);

        sleep(2);

        LOGGER.info("----- Step 2: OIOLedsageDokumentOpsplitningOpret");
        String ie825 = "split-ie825.xml";
        OIOLedsageDokumentOpsplitningOpretClient oioLedsageDokumentOpsplitningOpretClient = new OIOLedsageDokumentOpsplitningOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
        OIOLedsageDokumentOpsplitningOpretOType response2 = oioLedsageDokumentOpsplitningOpretClient.invoke(virksomhedSENummerIdentifikator,
                consignor, ie825, arc);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));
        assertFalse(response2.getOutput().getLedsageDokumentSamling().getLedsageDokument().isEmpty());

        sleep(2);

        LOGGER.info("----- Step 3: OIOLedsageDokumentOmdirigeretAdvisSamlingHent - Input: Date range");
        OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient client = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));
        OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType response3 = client.invoke(virksomhedSENummerIdentifikator,
                consignor, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10));
        assertFalse(hasError(response3.getHovedOplysningerSvar()));
        assertFalse(response3.getLedsageDokumentOmdirigeretAdvisSamling().getIE803BeskedTekst().isEmpty());
        assertTrue(response3.getLedsageDokumentOmdirigeretAdvisSamling().getIE803BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

        LOGGER.info("----- Step 4: OIOLedsageDokumentOmdirigeretAdvisSamlingHent - Input: ARC");
        OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient client4 = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));
        OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType response4 = client.invoke(virksomhedSENummerIdentifikator,
                consignor, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(arc));
        assertFalse(hasError(response4.getHovedOplysningerSvar()));
        assertFalse(response4.getLedsageDokumentOmdirigeretAdvisSamling().getIE803BeskedTekst().isEmpty());
        assertTrue(response4.getLedsageDokumentOmdirigeretAdvisSamling().getIE803BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

    }

}