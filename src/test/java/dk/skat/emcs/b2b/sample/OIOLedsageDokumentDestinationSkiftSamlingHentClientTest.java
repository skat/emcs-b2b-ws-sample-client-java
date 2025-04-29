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

/**
 * OIOLedsageDokumentDestinationSkiftSamlingHent Test
 * <p>
 * Purpose: Verify retrieval of IE813 messages.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE815 message (using OIOLedsageDokumentOpret).
 * Step 2: Submit IE813 message (using OIOLedsageDokumentDestinationSkiftOpret)
 * Step 3: Get IE813 message(s) (using OIOLedsageDokumentDestinationSkiftSamlingHent)
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentDestinationSkiftSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentDestinationSkiftSamlingHentClientTest.class.getName());

    @Test
    public void scenario() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException, InterruptedException {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentDestinationSkiftSamlingHent");

        if (endpointURL != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";

            // Step 1: OIOLedsageDokumentOpret
            // -------
                File ie815 = new File("change-dest-ie815.xml");
            OIOLedsageDokumentOpretClient client1 = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            OIOLedsageDokumentOpretOType response1 = client1.invoke2(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            assertFalse(hasError(response1.getHovedOplysningerSvar()));
            String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();
            assertNotNull("Did not receive ARC number. Exiting.", arc);
            LOGGER.info("Received ARC = " + arc);

            sleep(2);

            // Step 2: OIOLedsageDokumentDestinationSkiftOpret
            // -------
            String ie813 = "change-dest-ie813.xml";
            OIOLedsageDokumentDestinationSkiftOpretClient client2 = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint("OIOLedsageDokumentDestinationSkiftOpret"));
            OIOLedsageDokumentDestinationSkiftOpretOType response2 = client2.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
            assertFalse(hasError(response2.getHovedOplysningerSvar()));

            sleep(2);

            // Step 3: OIOLedsageDokumentDestinationSkiftSamlingHent
            // -------
            OIOLedsageDokumentDestinationSkiftSamlingHentClient client3 = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(endpointURL);
            OIOLedsageDokumentDestinationSkiftSamlingHentOType response3 = client3.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator);
            assertFalse(hasError(response3.getHovedOplysningerSvar()));
            assertFalse(response3.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst().isEmpty());
            assertTrue(response3.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst()
                    .stream().anyMatch(e -> (e.contains(arc))));
        }

    }

}