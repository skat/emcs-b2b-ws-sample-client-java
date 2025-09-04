package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEksportGodkendelseSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOEksportGodkendelseSamlingHent Test (IE829 as response)
 * <p>
 * IMPORTANT: This test case can first be run following steps completed
 * in the test system by the Danish Customs and Tax Administration.
 * <p>
 * Contact help desk and request test data for the service: OIOEksportGodkendelseSamlingHent
 */
public class OIOEksportGodkendelseSamlingHentClientTest extends BaseClientTest {

    // getSøgeParametreStrukturType(10)

    @Test
    public void searchByARC() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_EKSPORT_GODKENDELSE_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
        OIOEksportGodkendelseSamlingHentClient client = new OIOEksportGodkendelseSamlingHentClient(getEndpoint(OIO_EKSPORT_GODKENDELSE_SAMLING_HENT));
        OIOEksportGodkendelseSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType("25DKUA9GO984UJ7G8PE04"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getEksportGodkendelseSamling().getIE829BeskedTekst().isEmpty());
    }
}