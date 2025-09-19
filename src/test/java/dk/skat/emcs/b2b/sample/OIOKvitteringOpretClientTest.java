package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;

import static org.junit.Assume.assumeNotNull;

/**
 * OIOKvitteringOpretClient Test
 *
 * IMPORTANT: OIOLedsageDokumentSamlingHent is also tested as part of {@link OIOKvitteringSamlingHentClientTest#testScenario()}}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
        File ie818 = new File("ie818.xml");
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
        oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie818);
    }

}
