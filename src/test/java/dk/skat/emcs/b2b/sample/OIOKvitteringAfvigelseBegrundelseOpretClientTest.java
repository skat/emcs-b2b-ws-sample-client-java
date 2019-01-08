package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOKvitteringAfvigelseBegrundelseOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringAfvigelseBegrundelseOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOKvitteringAfvigelseBegrundelseOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE871 document is located
            String ie871 = "ie871.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(endpointURL);
            oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie871);
        } else {
            System.out.println("OIOKvitteringAfvigelseBegrundelseOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}