package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDocumentOpretClient Test
 *
 * @author SKAT
 * @since 1.0
 */
public class OIOLedsageDocumentOpretClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT");

        // Path to where the IE815 document is located
        String ie815 = "ie815.xml";
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = "30808460";
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";

        OIOLedsageDocumentOpretClient oioLedsageDocumentClient = new OIOLedsageDocumentOpretClient(endpointURL);
        oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);
    }

}