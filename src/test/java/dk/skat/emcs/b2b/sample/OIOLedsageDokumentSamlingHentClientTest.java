package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentSamlingHentOType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentSamlingHent Test
 * <p>
 * IMPORTANT: OIOLedsageDokumentSamlingHent is also tested as part of {@link OIOKvitteringSamlingHentClientTest#testScenario()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentSamlingHentClientTest extends BaseClientTest {

    // This tests assumes knowledge of existing ARC.
    @Test
    public void invoke() throws Exception {
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
        // The ARC number
        String ARCnumber = getConfig().getString("dk.skat.emcs.b2b.sample.ARC");
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOLedsageDokumentSamlingHentClient client = new OIOLedsageDokumentSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
        OIOLedsageDokumentSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ARCnumber);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

}
