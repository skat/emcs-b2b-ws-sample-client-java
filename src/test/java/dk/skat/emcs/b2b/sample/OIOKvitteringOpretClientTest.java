package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import java.io.File;

/**
 * OIOKvitteringOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringOpretClientTest extends BaseClient {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample.OIOKvitteringOpret.ENDPOINT");

        if (endpointURL != null) {
            File ie818 = new File ("ie818.xml");
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(endpointURL);
            oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator,ie818);
        } else {
            System.out.println("OIOKvitteringOpretClientTest: Endpoint not provided, skipping test");
        }
    }

}
