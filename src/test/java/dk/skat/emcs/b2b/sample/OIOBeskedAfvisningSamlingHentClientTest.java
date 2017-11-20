package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOBeskedAfvisningSamlingHentClientTest
 *
 * @author SKAT
 * @since 1.1
 */
public class OIOBeskedAfvisningSamlingHentClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOBeskedAfvisningSamlingHent.ENDPOINT");

        if (endpointURL != null) {
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(endpointURL);
            oioBeskedAfvisningSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
        } else {
            System.out.println("OIOBeskedAfvisningSamlingHentClientTest: Endpoint not provided, skipping test");
        }
    }

}
