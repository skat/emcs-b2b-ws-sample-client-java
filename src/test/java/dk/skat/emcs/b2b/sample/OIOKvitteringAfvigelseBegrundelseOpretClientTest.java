package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOKvitteringAfvigelseBegrundelseOpretOType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * OIOKvitteringAfvigelseBegrundelseOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringAfvigelseBegrundelseOpretClientTest extends BaseClientTest {

    @Test
    public void invoke() throws Exception {
        String endpointURL =
                getEndpoint("OIOKvitteringAfvigelseBegrundelseOpret");

        if (endpointURL != null) {

            // Path to where the IE871 document is located
            String ie871 = "ie871.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            String arc = "22DKTJSJ5HLFU0BAI7164";

            OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(endpointURL);
            oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie871, arc);
        }
    }

    /**
     * Demonstrate error code 425 if TradeExciseNumber in IE871 document does not match the AfgiftOperatoerPunktAfgiftIdentifikator field.
     */
    @Test
    public void testInvalidTraderExciseNumberInIE817() throws Exception {
        String endpointURL =
                getEndpoint("OIOKvitteringAfvigelseBegrundelseOpret");

        if (endpointURL != null) {

            // Path to where the IE871 document is located
            String ie871 = "ie871-invalid-TradeExciseNumber.xml";
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

            String arc = "22DKTJSJ5HLFU0BAI7164";

            OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(endpointURL);
            OIOKvitteringAfvigelseBegrundelseOpretOType response = oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie871, arc);
            assertTrue(hasError(response.getHovedOplysningerSvar(), 425));
        }
    }


}