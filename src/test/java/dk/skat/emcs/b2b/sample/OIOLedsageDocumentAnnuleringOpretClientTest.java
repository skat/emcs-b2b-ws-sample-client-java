package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentAnnulleringOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;

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
        String endpointURL = getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET);

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
    public void scenario() throws Exception {
        String endpointURL =
                getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET);

        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            // Step 1:
            // -------
            File ie815 = new File("ie815.xml");
            OIOLedsageDokumentOpretClient client1 = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            OIOLedsageDokumentOpretOType response1 = client1.invoke2(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            assertFalse(hasError(response1.getHovedOplysningerSvar()));
            String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();

            assertNotNull("Did not receive ARC number. Exiting.", arc);
            LOGGER.info("Received ARC = " + arc);

            sleep(2);

            // Step 2:
            // -------
            String ie810 = "ie810.xml";
            OIOLedsageDokumentAnnulleringOpretClient client2 = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
            OIOLedsageDokumentAnnulleringOpretOType response2 = client2.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);
            assertFalse(hasError(response2.getHovedOplysningerSvar()));

            sleep(2);

            // Step 3:
            // -------
            // Now trigger 704 - we have already cancelled the IE815 in the previous call.
            OIOLedsageDokumentAnnulleringOpretOType response3 = client2.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);
            assertTrue(hasError(response3.getHovedOplysningerSvar(), 491));

            // Step 4:
            // -------
            // Get the 704 and expect error to be "Message out of sequence"
            OIOBeskedAfvisningSamlingHentClient client3 = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
            OIOBeskedAfvisningSamlingHentOType response4 = client3.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, 1);
            assertTrue(response4.getBeskedAfvisningSamling().getIE704BeskedTekst()
                    .stream().anyMatch(e -> (e.contains(arc) && e.contains("Message out of sequence"))));
        }

    }


}