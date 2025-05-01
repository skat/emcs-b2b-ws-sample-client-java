package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentSamlingHentOType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * OIOLedsageDokumentSamlingHent Test
 * <p>
 * IMPORTANT: OIOLedsageDokumentSamlingHent is also tested as part of {@link OIOKvitteringSamlingHentClientTest#testScenario()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOLedsageDokumentSamlingHent");

        if (endpointURL != null) {

            // The ARC number
            String ARCnumber = getConfig().getString("dk.skat.emcs.b2b.sample.ARC");
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            OIOLedsageDokumentSamlingHentClient client = new OIOLedsageDokumentSamlingHentClient(endpointURL);
            OIOLedsageDokumentSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ARCnumber);
            assertFalse(hasError(response.getHovedOplysningerSvar()));
        }
    }

}
