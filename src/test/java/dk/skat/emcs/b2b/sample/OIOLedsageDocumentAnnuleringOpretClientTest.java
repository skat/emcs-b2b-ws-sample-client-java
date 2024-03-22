package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDocumentAnnuleringOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDocumentAnnuleringOpretClientTest.class.getName());

    /**
     * OIOLedsageDokumentAnnulleringOpret Test (IE810 as request)
     *
     * Purpose: Verify submit of IE810.
     *
     * Test case design steps:
     *
     * Step 1: Submit IE810 (using OIOLedsageDokumentAnnulleringOpret)
     *
     * IMPORTANT: OIOLedsageDokumentAnnulleringOpret is also tested as part of {@link OIOLedsageDokumentAnnulleringSamlingHentClientTest#invoke()}
     *
     * @author SKAT
     * @since 1.2
     */
    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET);

        if (endpointURL != null) {

            String ie810 = "ie810.xml";
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(endpointURL);
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, null);
        }
    }

    /**
     * Purpose: Verify submit of duplicate IE810 triggers IE704
     *
     * Test case design steps:
     *
     * Step 1: Submit IE815 (using OIOLedsageDokumentOpret)
     * Steo 2: Submit IE810 (using OIOLedsageDokumentAnnulleringOpret)
     * Step 3: Submit IE810 (using OIOLedsageDokumentAnnulleringOpret) to trigger IE704
     * Step 4: Call OIOBeskedAfvisningSamlingHent to get IE704
     */
    @Test
    public void triggerIE704() throws Exception {
        String endpointURL =
                getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET);

        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            // Step 1:
            // -------
            String arc = null;
            File ie815 = new File("ie815.xml");
            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);

            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815.");
            sleep(2);

            // Step 2:
            // -------
            String ie810 = "ie810.xml";
            OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);

            LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE810.");
            sleep(2);

            // Step 3:
            // -------
            // Now trigger 704 - we have already cancelled the IE815 in the previous call.
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);

            // Step 4:
            // -------
            // Get the 704 and expect error to be "Message out of sequence"
            OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
            oioBeskedAfvisningSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, 1);
        }

    }


}
