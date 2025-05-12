package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOHaendelseRapportSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOHaendelseRapportSamlingHent Test (IE840 as response)
 * <p>
 * IMPORTANT: This test case can first be run following steps completed
 * in the test system by the Danish Customs and Tax Administration.
 * <p>
 * Contact help desk and request test data for the service: OIOHaendelseRapportSamlingHent
 */
public class OIOHaendelseRapportSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_HAENDELSE_RAPPORT_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK99025875300";
        OIOHaendelseRapportSamlingHentClient client = new OIOHaendelseRapportSamlingHentClient(getEndpoint(OIO_HAENDELSE_RAPPORT_SAMLING_HENT));
        OIOHaendelseRapportSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType(20));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getHændelseRapportSamling().getIE840BeskedTekst().isEmpty());
    }
}