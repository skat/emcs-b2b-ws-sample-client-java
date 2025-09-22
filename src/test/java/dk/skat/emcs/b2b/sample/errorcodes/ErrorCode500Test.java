package dk.skat.emcs.b2b.sample.errorcodes;

import dk.skat.emcs.b2b.sample.BaseClientTest;
import dk.skat.emcs.b2b.sample.OIOBeskedAfvisningSamlingHentClient;
import dk.skat.emcs.b2b.sample.TransactionIdGenerator;
import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentOType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

public class ErrorCode500Test  extends BaseClientTest {

    @Test
    public void transactionIdReuse() throws Exception {
        assumeNotNull(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
        // Generate transaction id
        final String transactionID = TransactionIdGenerator.getTransactionId();
        // First call
        OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
        OIOBeskedAfvisningSamlingHentOType response = oioBeskedAfvisningSamlingHentClient.invoke(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator(), 1, transactionID);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        // Now try again
        OIOBeskedAfvisningSamlingHentOType response2 = oioBeskedAfvisningSamlingHentClient.invoke(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator(), 1, transactionID);
        assertTrue(hasError(response2.getHovedOplysningerSvar(), 500));
    }

}
