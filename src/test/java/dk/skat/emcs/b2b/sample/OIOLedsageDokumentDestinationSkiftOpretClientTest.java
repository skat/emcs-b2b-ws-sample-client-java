package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import static org.junit.Assume.assumeNotNull;

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
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
        String ie813 = "ie813.xml";
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";
        String arc = "11DKJKA05CB5I1EXW2KL9";
        OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
        client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
    }

}