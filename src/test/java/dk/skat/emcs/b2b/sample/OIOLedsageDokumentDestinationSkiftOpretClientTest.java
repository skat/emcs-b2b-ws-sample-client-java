package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftOpretOType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentDestinationSkiftOpret Test
 *
 * NOTE: OIOLedsageDokumentDestinationSkiftOpret is also tested as part of {@link OIOLedsageDokumentDestinationSkiftSamlingHentClientTest#scenario()}
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
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";
        String arc = "11DKJKA05CB5I1EXW2KL9";
        OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
        OIOLedsageDokumentDestinationSkiftOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie813, arc);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

}