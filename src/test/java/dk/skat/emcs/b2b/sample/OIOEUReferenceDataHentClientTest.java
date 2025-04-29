package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEUReferenceDataAnmodOType;
import oio.skat.emcs.ws._1_0.OIOEUReferenceDataHentOType;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * OIOEUReferenceDataHent Test (IE733 as response)
 * <p>
 * Purpose: Verify fetch of IE733
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE705 (using OIOEUReferenceDataAnmod)
 * Step 2: Fetch IE733 (using OIOEUReferenceDataHent)
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOEUReferenceDataHentClientTest extends BaseClientTest {

    @Test
    public void scenario() throws Exception {
        if (getEndpoint(OIO_EUREFERENCE_DATA_HENT) != null) {
            final String beskedIdentifikator = UUID.randomUUID().toString();
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
            // Step 1:
            // -------
            OIOEUReferenceDataAnmodClient client1 = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
            OIOEUReferenceDataAnmodOType response1 = client1.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
            assertFalse(hasError(response1.getHovedOplysningerSvar()));

            // Step 2:
            // -------
            OIOEUReferenceDataHentClient client2 = new OIOEUReferenceDataHentClient(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
            OIOEUReferenceDataHentOType response2 = client2.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
            assertFalse(hasError(response2.getHovedOplysningerSvar()));
            assertNotNull(response2.getIE733BeskedTekst());
        }
    }
}