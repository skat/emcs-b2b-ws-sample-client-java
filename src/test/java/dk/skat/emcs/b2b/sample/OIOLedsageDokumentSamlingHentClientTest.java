package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentSamlingHent Test
 *
 * IMPORTANT: OIOLedsageDokumentSamlingHent is also tested as part of {@link OIOKvitteringSamlingHentClientTest#invoke()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentSamlingHent");

        if (endpointURL != null) {

            // The ARC number
            String ARCnumber = getConfig().getString("dk.skat.emcs.b2b.sample.ARC");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentSamlingHentClient ledsageDokumentSamlingHentClient = new OIOLedsageDokumentSamlingHentClient(endpointURL);
            ledsageDokumentSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ARCnumber);
        }
    }

}
