package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOForsendelseAfbrydelseBeskedSamlingHentOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOForsendelseAfbrydelseBeskedSamlingHent Test (IE807 as response)
 * <p>
 * Contact help desk and request test data for the service: OIOForsendelseAfbrydelseBeskedSamlingHent
 */
public class OIOForsendelseAfbrydelseBeskedSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {
        assumeNotNull(getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
        OIOForsendelseAfbrydelseBeskedSamlingHentClient client = new OIOForsendelseAfbrydelseBeskedSamlingHentClient(getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT));
        OIOForsendelseAfbrydelseBeskedSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType("25DK5MSE6W7L2HDWLH200"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getForsendelseAfbrydelseBeskedSamling().getIE807BeskedTekst().isEmpty());
        assertEquals(1, response.getForsendelseAfbrydelseBeskedSamling().getIE807BeskedTekst().size());
    }

}