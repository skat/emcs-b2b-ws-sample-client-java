package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOEksportAngivelseInvalideringNotifikationSamlingHent Test
 *
 */
public class OIOEksportAngivelseInvalideringNotifikationSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOEksportAngivelseInvalideringNotifikationSamlingHent");

        if (endpointURL != null) {

            // The ARC number
            String ARCnumber = getConfig().getString("dk.skat.emcs.b2b.sample.ARC");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOEksportAngivelseInvalideringNotifikationSamlingHentClient client = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ARCnumber);
        }
    }

}
