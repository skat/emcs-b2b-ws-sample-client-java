package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentDestinationSkiftSamlingHent Test
 * <p>
 * Purpose: Verify retrieval of IE813 messages.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 message (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE813 message (using OIOLedsageDokumentDestinationSkiftOpret)
 * Step 3: Get IE813 message(s) (using OIOLedsageDokumentDestinationSkiftSamlingHent) using date range as search param
 * Step 4: Get IE813 message (using OIOLedsageDokumentDestinationSkiftSamlingHent) using ARC as search param
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentDestinationSkiftSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentDestinationSkiftSamlingHentClientTest.class.getName());

    @Test
    public void scenario() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException, InterruptedException {
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";

        LOGGER.info("----- Step 1: OIOLedsageDokumentOpret");
        // -------
        File ie815 = new File("change-dest-ie815.xml");
        OIOLedsageDokumentOpretClient client1 = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        OIOLedsageDokumentOpretOType response1 = client1.invoke2(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        assertFalse(hasError(response1.getHovedOplysningerSvar()));
        String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();
        assertNotNull("Did not receive ARC number. Exiting.", arc);
        LOGGER.info("Received ARC = " + arc);

        sleep(2);

        LOGGER.info("----- Step 2: OIOLedsageDokumentDestinationSkiftOpret");
        String ie813 = "change-dest-ie813.xml";
        OIOLedsageDokumentDestinationSkiftOpretClient client2 = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
        OIOLedsageDokumentDestinationSkiftOpretOType response2 = client2.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));

        sleep(2);

        LOGGER.info("----- Step 3: OIOLedsageDokumentDestinationSkiftSamlingHent - Input: Date range");
        OIOLedsageDokumentDestinationSkiftSamlingHentClient client3 = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT));
        OIOLedsageDokumentDestinationSkiftSamlingHentOType response3 = client3.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10));
        assertFalse(hasError(response3.getHovedOplysningerSvar()));
        assertFalse(response3.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst().isEmpty());
        assertTrue(response3.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

        LOGGER.info("----- Step 4: OIOLedsageDokumentDestinationSkiftSamlingHent- Input: ARC");
        OIOLedsageDokumentDestinationSkiftSamlingHentClient client4 = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT));
        OIOLedsageDokumentDestinationSkiftSamlingHentOType response4 = client4.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(arc));
        assertFalse(hasError(response4.getHovedOplysningerSvar()));
        assertFalse(response4.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst().isEmpty());
        assertTrue(response4.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

    }

}