package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentOType;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * OIOBeskedAfvisningSamlingHentClientTest
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOBeskedAfvisningSamlingHentClientTest extends BaseClientTest {

    // EXCISE NUMBER DK82065873300 -> CVR 19552101 / SE 19552101.´
    @Test
    public void testDelegation() throws Exception {
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

    // EXCISE NUMBER DK19552101100 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK19552101100");
        assertTrue(hasError(response.getHovedOplysningerSvar()));
    }

    // EXCISE NUMBER DK19552101500 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess500() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK19552101500");
        assertTrue(hasError(response.getHovedOplysningerSvar()));
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

    // EXCISE NUMBER DK19552101300 -> CVR 19552101 / SE 30808460
    @Test
    public void testNoDelegationSENotRelatedToCVR() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall("30808460", "DK19552101300");
        assertTrue(hasError(response.getHovedOplysningerSvar(), 300));
    }


    // EXCISE NUMBER DK3117514300 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode102WrongExciseNumberFormat() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK3117514300"); // last '0' missing
        assertTrue(hasError(response.getHovedOplysningerSvar(), 102));
    }

    // EXCISE NUMBER DK31175143999 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode301WrongExciseNumberSuffixCode() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK31175143999");
        assertTrue(hasError(response.getHovedOplysningerSvar(), 301));
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

    // EXCISE NUMBER DK99025875600 -> CVR 19552101 / SE 31038421
    @Test
    public void testErrorCode302ExciseNumberHasNotDelegatedToSENumberNotEqualsCVR() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall("31038421", getAfgiftOperatoerPunktAfgiftIdentifikator());
        assertTrue(hasError(response.getHovedOplysningerSvar(), 302));
    }

    // EXCISE NUMBER DK30808460300 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode302ExciseNumberHasNotDelegatedToSENumberEqualsCVR() throws Exception {
        OIOBeskedAfvisningSamlingHentOType response =
                doCall(getVirksomhedSENummerIdentifikator(), "DK30808460300");
        assertTrue(hasError(response.getHovedOplysningerSvar(), 302));
    }

    public OIOBeskedAfvisningSamlingHentOType doCall(String virksomhedSENummerIdentifikator, String afgiftOperatoerPunktAfgiftIdentifikator) throws Exception {
        String endpointURL = getEndpoint("OIOBeskedAfvisningSamlingHent");
        if (endpointURL != null) {
            OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(endpointURL);
            return oioBeskedAfvisningSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator);
        }
        return null;
    }

}
