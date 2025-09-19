package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import static org.junit.Assume.assumeNotNull;

/**
 * OIOLedsageDokumentNotifikationOpretClient Test
 *
 * IMPORTANT: OIOLedsageDokumentNotifikationOpret is also tested as part of {@link OIOLedsageDokumentNotifikationSamlingHentClientTest#scenario()}
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
        OIOLedsageDokumentNotifikationOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie819);
    }

}