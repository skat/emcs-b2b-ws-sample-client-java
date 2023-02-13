package dk.skat.emcs.b2b.sample;

import org.junit.Test;

/**
 * OIOLedsageDokumentDestinationSkiftOpret Test
 *
 * IMPORTANT: OIOLedsageDokumentDestinationSkiftOpret is also tested as part of {@link OIOLedsageDokumentDestinationSkiftSamlingHentClientTest#invoke()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentDestinationSkiftOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentDestinationSkiftOpret");

        if (endpointURL != null) {

            // Path to where the IE813 document is located
            String ie813 = "ie813.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300"; //getAfgiftOperatoerPunktAfgiftIdentifikator();
            String arc = null;
            OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
        }
    }

}