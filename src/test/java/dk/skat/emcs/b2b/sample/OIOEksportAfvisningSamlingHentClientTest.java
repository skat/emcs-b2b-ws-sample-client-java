package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

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
        String endpointURL =
                getEndpoint("OIOEksportAfvisningSamlingHent");

        if (endpointURL != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
            OIOEksportAfvisningSamlingHentClient client = new OIOEksportAfvisningSamlingHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }
}