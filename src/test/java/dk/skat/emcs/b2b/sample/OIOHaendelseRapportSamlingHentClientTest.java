package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOHaendelseRapportSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static org.junit.Assert.assertFalse;

/**
 * OIOHaendelseRapportSamlingHent Test (IE840 as response)
 * <p>
 * IMPORTANT: This test case can first be run following steps completed
 * in the test system by the Danish Customs and Tax Administration.
 * <p>
 * Contact help desk and request test data for the service: OIOHaendelseRapportSamlingHent
 *
 */
public class OIOHaendelseRapportSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {
        String endpointURL =
                getEndpoint("OIOHaendelseRapportSamlingHent");

        if (endpointURL != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator =  "DK99025875300";
            OIOHaendelseRapportSamlingHentClient client = new OIOHaendelseRapportSamlingHentClient(endpointURL);
            OIOHaendelseRapportSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
            assertFalse(hasError(response.getHovedOplysningerSvar()));
        }

    }
}