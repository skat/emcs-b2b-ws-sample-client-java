package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * OIOLedsageDocumentOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDocumentOpretClientTest extends BaseClientTest {

    private String endpointURL = getEndpoint("OIOLedsageDocumentOpret");

    /**
     * Test submit of ie815 - no delegation
     *
     * Expected Console Output (sample):
     *
     * *******************************************************************
     * ** HovedOplysningerSvar
     * **** Transaction Id: e0372c6a-8ba6-4d1e-ae63-d813c2df851c
     * **** Transaction Time: 2019-02-19T09:11:24.973+01:00
     * **** Service Identification: FS2_OIOLedsageDokumentOpret
     * *******************************************************************
     * Ledsagedokument Valideret Dato: 2019-02-19Z
     * Ledsagedokument ARC Identifikator: 19DKULQD05U6R1JKDX8Q4
     * *******************************************************************
     */
    @Test
    public void invoke() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815.xml");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        }
    }

    @Test
    public void invoke19552101() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815-19552101.xml");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK19552101100";

            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        }
    }

    /**
     * Test submit of ie815 - valid delegation
     *
     * Expected Console Output (sample):
     *
     * *******************************************************************
     * ** HovedOplysningerSvar
     * **** Transaction Id: ad7ca908-6ed7-4b3b-994f-20b3beed6af2
     * **** Transaction Time: 2019-02-19T12:34:33.009+01:00
     * **** Service Identification: FS2_OIOLedsageDokumentOpret
     * *******************************************************************
     * Ledsagedokument Valideret Dato: 2019-02-19Z
     * Ledsagedokument ARC Identifikator: 19DKN7XC27BNVA42XMD51
     * *******************************************************************
     */
    @Test
    public void invokeValidDelegation() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815-valid-delegation.xml");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";

            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        }
    }

    /**
     * Test submit of ie815 - CVR number is not parent to VAT number in VirksomhedSENummerIdentifikator
     *
     * Expected Console Output:
     *
     * Error code = 300
     * Error Text = SE nummeret 12345678 har ingen tilhoersforhold til det anvendte certifikat.
     */
    @Test
    public void invokeCertificateHasNoRelationToVirksomhedSENummerIdentifikator() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815.xml");
            // This SE does not match CVR of certificate.
            String virksomhedSENummerIdentifikator = "12345678";
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(endpointURL);
            oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        }
    }

    /**
     * Test submit of ie815 - no delegation exists
     *
     * Expected Console Output:
     *
     * Error Code: 302
     * Error Text: Den angivet afgift operatoer har enten ikke tillades til at udfoere denne operation eller har ikke delegeret sine rettigheder til det firma hvis certifikat er anvendt i kaldet af webservicen
     */
    @Test
    public void invokeCertificateHasNoDelegation() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815-no-delegation.xml");
            // This SE does not match CVR of certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK12345678300";

            OIOLedsageDokumentOpretClient client = new OIOLedsageDokumentOpretClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        }
    }

    /**
     * Trigger error 104
     *
     * Expected Console Output:
     *
     * Error Code: 104
     * Error Text: Den angivet afgift operatoer stemmer ikke overens med vareafsender (consignor) i IE815 beskeden
     */
    @Test
    public void invokeCertificatTriggerError104() throws Exception {
        if (endpointURL != null) {
            // Path to where the IE815 document is located
            File ie815 = new File("ie815-trigger-error-104.xml");
            // This SE does not match CVR of certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentOpretClient client = new OIOLedsageDokumentOpretClient(endpointURL);
            OIOLedsageDokumentOpretOType response = client.invoke2(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            assertTrue(hasError(response.getHovedOplysningerSvar(), 104));
        }
    }


}