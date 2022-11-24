package dk.skat.emcs.b2b.sample;

import org.junit.Test;

public class OIOEUReferenceDataHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOEUReferenceDataHent");

        if (endpointURL != null) {
            String beskedIdentifikator = "b689acb2-a873-411c-853d-d521fc9b06ce";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";

            OIOEUReferenceDataHentClient client = new OIOEUReferenceDataHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
        }
    }
}