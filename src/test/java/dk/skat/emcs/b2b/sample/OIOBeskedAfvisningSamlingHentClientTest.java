package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentOType;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOBeskedAfvisningSamlingHentClientTest
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOBeskedAfvisningSamlingHentClientTest extends BaseClientTest {

    // EXCISE NUMBER DK82065873300 -> CVR 19552101 / SE 19552101.´
    @Test
    public void invoke() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator());
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK82065873300 -> CVR 19552101 / SE 10200113
    @Test
    public void testDelegationSEDifferentFromCVR() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall("10200113", getAfgiftOperatoerPunktAfgiftIdentifikator());
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK19552101300 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationNoAccess() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK19552101300");
        assertFalse(hasError(response.getHovedOplysningerSvar(), 302));
    }

    // EXCISE NUMBER DK19552101200 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess200() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK19552101200");
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK19552101600 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess600() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK19552101600");
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK31175143500 -> CVR 19552101 / SE 19552101
    @Test
    public void testExciseNumberSuffixCode500() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK31175143500");
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK99025875600 -> CVR 19552101 / SE 19552101
    @Test
    public void testExciseNumberSuffixCode600() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK99025875600");
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    public OIOBeskedAfvisningSamlingHentOType doCall(String virksomhedSENummerIdentifikator, String afgiftOperatoerPunktAfgiftIdentifikator) throws Exception {
        assumeNotNull(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
        OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
        return oioBeskedAfvisningSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator);
    }

}
