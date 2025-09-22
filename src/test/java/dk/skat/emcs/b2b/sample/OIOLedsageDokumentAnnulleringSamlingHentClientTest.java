package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentAnnulleringSamlingHentOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentAnnulleringSamlingHent Test
 * <p>
 * Purpose: Verify retrieval of IE810 messages.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE810 (using OIOLedsageDokumentAnnulleringOpret)
 * Step 3: Get IE810 documents - using data range as search param
 * Step 4: Get IE810 documents - using ARC as search param
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentAnnulleringSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentAnnulleringSamlingHentClientTest.class.getName());

    @Test
    public void scenario() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException, InterruptedException {
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
        assumeNotNull(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));

        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

        LOGGER.info("----- Step 1: OIOLedsageDokumentOpret");
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        String arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);

        assertNotNull("Did not receive ARC number. Exiting.", arc);

        LOGGER.info("Received ARC = " + arc);
        LOGGER.info("Sleeping 2 minutes to allow EMCS process the IE815.");
        sleep(2);

        LOGGER.info("----- Step 2: OIOLedsageDokumentAnnulleringOpret");
        OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
        oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, "ie810.xml", arc);

        LOGGER.info("Sleeping 1 minute to allow EMCS process the IE810.");
        sleep(1);

        LOGGER.info("----- Step 3: OIOLedsageDokumentAnnulleringSamlingHent - Input: Date range");
        OIOLedsageDokumentAnnulleringSamlingHentClient client3 = new OIOLedsageDokumentAnnulleringSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
        OIOLedsageDokumentAnnulleringSamlingHentOType response3 = client3.invoke(
                virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType(1)
        );
        assertFalse(hasError(response3.getHovedOplysningerSvar()));
        assertFalse(response3.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst().isEmpty());
        assertTrue(response3.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

        LOGGER.info("----- Step 4: OIOLedsageDokumentAnnulleringSamlingHent - Input: ARC");
        OIOLedsageDokumentAnnulleringSamlingHentClient client4 = new OIOLedsageDokumentAnnulleringSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
        OIOLedsageDokumentAnnulleringSamlingHentOType response4 = client4.invoke(
                virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType(arc));
        assertFalse(hasError(response4.getHovedOplysningerSvar()));
        assertFalse(response4.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst().isEmpty());
        assertTrue(response4.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

    }
}