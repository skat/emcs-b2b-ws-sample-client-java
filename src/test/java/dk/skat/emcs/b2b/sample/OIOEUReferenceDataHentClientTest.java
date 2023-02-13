package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.util.UUID;

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
    public void invoke() throws Exception {

        if (getEndpoint(OIO_EUREFERENCE_DATA_HENT) != null) {
            final String beskedIdentifikator = UUID.randomUUID().toString();
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
            // Step 1:
            // -------
            {
                OIOEUReferenceDataAnmodClient client = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
                client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
            }
            // Step 2:
            // -------
            {
                OIOEUReferenceDataHentClient client = new OIOEUReferenceDataHentClient(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
                client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
            }
        }
    }
}