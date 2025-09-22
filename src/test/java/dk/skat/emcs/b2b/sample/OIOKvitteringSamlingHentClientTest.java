package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOKvitteringOpretOType;
import oio.skat.emcs.ws._1_0.OIOKvitteringSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.util.logging.Logger;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

public class OIOKvitteringSamlingHentClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOKvitteringSamlingHentClientTest.class.getName());

    /**
     * NOTE: Use this unit test method to manually test fetching IE801 documents.
     * <p>
     * OIOKvitteringSamlingHentClient is also tested as part of scenario {@link OIOKvitteringSamlingHentClientTest#scenario()}}
     * where prerequisite documents are submitted as part of the unit test.
     */
    @Test
    public void invoke() throws DatatypeConfigurationException {
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
        OIOKvitteringSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, getSøgeParametreStrukturType(10));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    @Test
    public void scenario() throws Exception {
        // Services tested in this scenario:
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
        assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
        assumeNotNull(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String consignor = getAfgiftOperatoerPunktAfgiftIdentifikator();

        // Call OIOLedsageDokumentOpret as Consignor
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient client1 = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        OIOLedsageDokumentOpretOType response1 = client1.invoke2(virksomhedSENummerIdentifikator,
                consignor, ie815);
        String arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();
        assertFalse(hasError(response1.getHovedOplysningerSvar()));

        assertNotNull("Did not receive ARC number. Exiting.", arc);
        LOGGER.info("Received ARC = " + arc);

        sleep(2);

        String consignee = "DK99025875300";

        // Call OIOLedsageDokumentSamlingHent as Consignee
        OIOLedsageDokumentSamlingHentClient client2 = new OIOLedsageDokumentSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
        OIOLedsageDokumentSamlingHentOType response2 = client2.invoke(virksomhedSENummerIdentifikator, consignee, arc);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));
        assertFalse(response2.getLedsageDokumentStamDataSamling().getIE801BeskedTekst().isEmpty());
        assertTrue(response2.getLedsageDokumentStamDataSamling().getIE801BeskedTekst().stream().anyMatch(e -> e.contains(arc)));

        // Call OIOKvitteringOpret as Consignee
        File ie818 = new File("ie818.xml");
        OIOKvitteringOpretClient client3 = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
        OIOKvitteringOpretOType response3 = client3.invoke(virksomhedSENummerIdentifikator, consignee, ie818, arc);
        assertFalse(hasError(response3.getHovedOplysningerSvar()));

        sleep(2);

        // Call OIOKvitteringSamlingHent as Consignee
        OIOKvitteringSamlingHentClient client4 = new OIOKvitteringSamlingHentClient(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
        OIOKvitteringSamlingHentOType response4 = client4.invoke(virksomhedSENummerIdentifikator, consignee, arc);
        assertFalse(hasError(response4.getHovedOplysningerSvar()));
        assertFalse(hasAdvis(response4.getHovedOplysningerSvar(), 130));
        assertFalse(response4.getKvitteringSamling().getIE818BeskedTekst().isEmpty());
        assertTrue(response4.getKvitteringSamling().getIE818BeskedTekst().stream().anyMatch(e -> e.contains(arc)));
    }
}