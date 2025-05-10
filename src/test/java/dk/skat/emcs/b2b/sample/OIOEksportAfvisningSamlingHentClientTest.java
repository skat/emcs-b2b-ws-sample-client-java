package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEksportAfvisningSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOEksportAfvisningSamlingHent Test (IE839 as response)
 *
 * IMPORTANT: This test case can first be run following steps completed
 * in the test system by the Danish Customs and Tax Administration.
 *
 * Contact help desk and request test data for the service: OIOEksportAfvisningSamlingHent
 *
 */
public class OIOEksportAfvisningSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
        OIOEksportAfvisningSamlingHentClient client = new OIOEksportAfvisningSamlingHentClient(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
        OIOEksportAfvisningSamlingHentOType response = client.invoke(getVirksomhedSENummerIdentifikator(),
                afgiftOperatoerPunktAfgiftIdentifikator);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(!response.getEksportAfvisningSamling().getIE839BeskedTekst().isEmpty());
    }

    @Test
    public void testAdvisCode130() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
        OIOEksportAfvisningSamlingHentClient client = new OIOEksportAfvisningSamlingHentClient(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
        OIOEksportAfvisningSamlingHentOType response = client.invoke(getVirksomhedSENummerIdentifikator(),
                afgiftOperatoerPunktAfgiftIdentifikator, getSearchPeriodInFuture());
        assertTrue(hasAdvis(response.getHovedOplysningerSvar(), 130));
    }
}