package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentNotifikationOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentNotifikationOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDokumentNotifikationOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE819 document is located
            String ie819 = "ie819.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentNotifikationOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentNotifikationOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie819);
        } else {
            System.out.println("OIOLedsageDokumentNotifikationOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}