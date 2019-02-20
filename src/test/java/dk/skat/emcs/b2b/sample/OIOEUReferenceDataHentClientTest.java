package dk.skat.emcs.b2b.sample;

import org.junit.Test;

public class OIOEUReferenceDataHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOEUReferenceDataHent");

        if (endpointURL != null) {
            String beskedIdentifikator = "123456789ABCD";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOEUReferenceDataHentClient client = new OIOEUReferenceDataHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
        }
    }
}