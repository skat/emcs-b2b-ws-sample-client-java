package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOKvitteringOpretOType;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOKvitteringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringOpretClientTest extends BaseClientTest {

    /**
     * NOTE: Use this unit test method to manually test submitting IE818 documents manually.
     * <p>
     * OIOKvitteringOpretClient is also tested as part of scenario {@link OIOKvitteringSamlingHentClientTest#scenario()}}
     * where prerequisite documents are submitted as part of the unit test.
     */
    @Test
    public void invoke() throws Exception {
        assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
        File ie818 = new File("ie818.xml");
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOKvitteringOpretClient client = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
        OIOKvitteringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie818, "17DKK1KHPMQH2W23ABI62");
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

}
