package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentAnnulleringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDocumentAnnuleringOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentAnnulleringOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE810 document is located
            String ie810 = "ie810.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(endpointURL);
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810);
        } else {
            System.out.println("OIOLedsageDocumentAnnulleringOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}