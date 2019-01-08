package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;

/**
 * OIOLedsageDocumentOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDocumentOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE815 document is located
            File ie815 = new File("ie815.xml");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDocumentOpretClient oioLedsageDocumentClient = new OIOLedsageDocumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        } else {
            System.out.println("OIOLedsageDocumentOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}