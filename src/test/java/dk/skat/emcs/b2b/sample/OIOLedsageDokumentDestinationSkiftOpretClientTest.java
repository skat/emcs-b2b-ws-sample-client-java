package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentDestinationSkiftOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentDestinationSkiftOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOLedsageDokumentDestinationSkiftOpret.ENDPOINT");

        if (endpointURL != null) {

            // Path to where the IE813 document is located
            String ie813 = "ie813.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentDestinationSkiftOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentDestinationSkiftOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie813);
        } else {
            System.out.println("OIOLedsageDokumentDestinationSkiftOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}