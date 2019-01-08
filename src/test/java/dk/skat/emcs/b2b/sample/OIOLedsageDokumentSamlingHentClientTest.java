package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentSamlingHentClientTest
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentSamlingHentClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDokumentSamlingHent.ENDPOINT");

        if (endpointURL != null) {

            // The ARC number
            String ARCnumber =   System.getProperty("dk.skat.emcs.b2b.sample.ARC");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentSamlingHentClient ledsageDokumentSamlingHentClient = new OIOLedsageDokumentSamlingHentClient(endpointURL);
            ledsageDokumentSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ARCnumber);
        } else {
            System.out.println("OIOLedsageDokumentSamlingHentClientTest: Endpoint not provided, skipping test");
        }
    }

}
