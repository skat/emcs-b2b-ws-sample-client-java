package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentOpsplitningOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentOpsplitningOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDokumentOpsplitningOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE825 document is located
            String ie825 = "ie825.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentOpsplitningOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentOpsplitningOpretClient(endpointURL);
            oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie825);
        } else {
            System.out.println("OIOLedsageDokumentOpsplitningOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}