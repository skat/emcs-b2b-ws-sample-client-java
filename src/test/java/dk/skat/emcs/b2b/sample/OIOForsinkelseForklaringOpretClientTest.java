package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOForsinkelseForklaringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOForsinkelseForklaringOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOForsinkelseForklaringOpret");
        if (endpointURL != null) {
            // Path to where the IE837 document is located
            String ie837 = "ie837.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300"; //getAfgiftOperatoerPunktAfgiftIdentifikator();

            String arc = "22DKQ68LFULXD4F11S652";

            OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringClient = new OIOForsinkelseForklaringOpretClient(endpointURL);
            oioForsinkelseForklaringClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie837, arc, afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }

}