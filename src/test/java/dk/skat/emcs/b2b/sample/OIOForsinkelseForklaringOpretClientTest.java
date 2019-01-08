package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOForsinkelseForklaringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOForsinkelseForklaringOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOForsinkelseForklaringOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE837 document is located
            String ie837 = "ie837.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringClient = new OIOForsinkelseForklaringOpretClient(endpointURL);
            oioForsinkelseForklaringClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie837);
        } else {
            System.out.println("OIOForsinkelseForklaringOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}