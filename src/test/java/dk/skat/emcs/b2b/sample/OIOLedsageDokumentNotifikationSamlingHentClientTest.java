package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationOpretOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * 1. Call OIOLedsageDokumentOpret (IE815)
 * 2. Call OIOLedsageDokumentNotifikationOpret (IE819)
 * 3. Call OIOLedsageDokumentNotifikationSamlingHent (IE819) using date range as search param
 * 4. Call OIOLedsageDokumentNotifikationSamlingHent (IE819) using ARC as search param
 */
public class OIOLedsageDokumentNotifikationSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException, InterruptedException, IOException, SAXException, ParserConfigurationException {
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
        OIOLedsageDokumentNotifikationSamlingHentClient client = new OIOLedsageDokumentNotifikationSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));
        OIOLedsageDokumentNotifikationSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst().isEmpty());
    }

    @Test
    public void scenario() throws DatatypeConfigurationException, InterruptedException, IOException, SAXException, ParserConfigurationException {
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));

        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";

        String arc;
        // Step 1:
        // -------
        File ie815 = new File("for-notification-ie815.xml");
        OIOLedsageDokumentOpretClient client1 = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        OIOLedsageDokumentOpretOType response1 = client1.invoke2(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        arc = response1.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();

        assertNotNull("Did not receive ARC number. Exiting.", arc);

        sleep(2);

        // Step 2:
        // -------
        String ie819 = "for-notification-ie819.xml";
        OIOLedsageDokumentNotifikationOpretClient client2 = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        OIOLedsageDokumentNotifikationOpretOType response2 = client2.invoke(virksomhedSENummerIdentifikator,
                "DK99025875300", ie819, arc);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));

        sleep(2);

        // Step 3:
        // -------
        OIOLedsageDokumentNotifikationSamlingHentClient client3 = new OIOLedsageDokumentNotifikationSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));
        OIOLedsageDokumentNotifikationSamlingHentOType response3 = client3.invoke(
                virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10));
        assertFalse(hasError(response3.getHovedOplysningerSvar()));
        assertFalse(response3.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst().isEmpty());
        assertTrue(response3.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));

        // Step 4:
        // -------
        OIOLedsageDokumentNotifikationSamlingHentOType response4 = client3.invoke(
                virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(arc));
        assertFalse(hasError(response4.getHovedOplysningerSvar()));
        assertFalse(response4.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst().isEmpty());
        assertTrue(response4.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst()
                .stream().anyMatch(e -> (e.contains(arc))));
    }
}