package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentNotifikationOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentNotifikationOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentNotifikationOpret");

        if (endpointURL != null) {

            // Path to where the IE819 document is located
            String ie819 = "ie819.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK99025875300";

            OIOLedsageDokumentNotifikationOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentNotifikationOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie819);
        }
    }

}