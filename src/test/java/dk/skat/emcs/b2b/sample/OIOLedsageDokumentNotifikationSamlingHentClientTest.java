package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

public class OIOLedsageDokumentNotifikationSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentNotifikationSamlingHent");

        if (endpointURL != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK99025875300";
            OIOLedsageDokumentNotifikationSamlingHentClient client = new OIOLedsageDokumentNotifikationSamlingHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }
}