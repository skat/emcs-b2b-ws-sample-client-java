package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEUReferenceDataAnmodOType;
import oio.skat.emcs.ws._1_0.OIOEUReferenceDataHentOType;
import org.junit.Test;

import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

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

    private static final Logger LOGGER = Logger.getLogger(OIOEUReferenceDataHentClientTest.class.getName());

    @Test
    public void scenario() throws Exception {
        assumeNotNull(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
        assumeNotNull(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
        final String beskedIdentifikator = UUID.randomUUID().toString();
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
        LOGGER.info("----- Step 1: OIOEUReferenceDataAnmod");
        OIOEUReferenceDataAnmodClient client1 = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
        OIOEUReferenceDataAnmodOType response1 = client1.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
        assertFalse(hasError(response1.getHovedOplysningerSvar()));

        LOGGER.info("----- Step 2: OIOEUReferenceDataHent");
        OIOEUReferenceDataHentClient client2 = new OIOEUReferenceDataHentClient(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
        OIOEUReferenceDataHentOType response2 = client2.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
        assertFalse(hasError(response2.getHovedOplysningerSvar()));
        assertNotNull(response2.getIE733BeskedTekst());
    }
}