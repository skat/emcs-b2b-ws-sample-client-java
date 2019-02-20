package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import java.io.File;

/**
 * OIOKvitteringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOKvitteringOpret");
        if (endpointURL != null) {
            File ie818 = new File ("ie818.xml");
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(endpointURL);
            oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator,ie818);
        }
    }

}
