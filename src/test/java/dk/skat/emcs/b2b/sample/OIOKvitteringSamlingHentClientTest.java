package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

public class OIOKvitteringSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {
        String endpointURL =
                getEndpoint("OIOKvitteringSamlingHent");
        if (endpointURL != null) {
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, null);
        }
    }
}