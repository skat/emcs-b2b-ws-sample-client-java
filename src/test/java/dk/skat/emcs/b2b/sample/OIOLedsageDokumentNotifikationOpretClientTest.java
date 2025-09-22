package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationOpretOType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentNotifikationOpretClient Test
 *
 * NOTE: OIOLedsageDokumentNotifikationOpret is also tested as part of {@link OIOLedsageDokumentNotifikationSamlingHentClientTest#scenario()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentNotifikationOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        String ie819 = "ie819.xml";
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK99025875300";
        String arc = "17DKK1KHPMQH2W23ABI62";
        OIOLedsageDokumentNotifikationOpretClient client = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        OIOLedsageDokumentNotifikationOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie819, arc);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

}