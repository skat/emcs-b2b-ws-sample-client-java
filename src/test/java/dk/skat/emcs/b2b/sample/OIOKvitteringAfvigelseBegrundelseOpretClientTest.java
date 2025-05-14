package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOKvitteringAfvigelseBegrundelseOpretOType;
import oio.skat.emcs.ws._1_0.OIOKvitteringOpretOType;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOKvitteringAfvigelseBegrundelseOpretClient Test
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOKvitteringAfvigelseBegrundelseOpretClientTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(OIOKvitteringAfvigelseBegrundelseOpretClientTest.class.getName());

    @Test
    public void scenario() throws Exception {
        assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
        assumeNotNull(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));

        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

        LOGGER.info("----- Step 1: OIOLedsageDokumentOpret");
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        String arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);
        assertNotNull("Did not receive ARC number. Exiting.", arc);

        sleep(2);

        LOGGER.info("----- Step 2: OIOKvitteringOpret");
        String consignee = "DK99025875300";
        File ie818 = new File ("ie818-shortage.xml");
        OIOKvitteringOpretClient client3 = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
        OIOKvitteringOpretOType response3 = client3.invoke(virksomhedSENummerIdentifikator, consignee,ie818, arc);
        printExplanationIfError491(response3.getHovedOplysningerSvar(), virksomhedSENummerIdentifikator, consignee, arc);
        assertFalse(hasError(response3.getHovedOplysningerSvar()));

        sleep(2);

        LOGGER.info("----- Step 3: OIOKvitteringAfvigelseBegrundelseOpret");
        // Path to where the IE871 document is located
        String ie871 = "ie871.xml";
        OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
        OIOKvitteringAfvigelseBegrundelseOpretOType response = oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie871, arc);
        printExplanationIfError491(response3.getHovedOplysningerSvar(), virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, arc);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }

    /**
     * Demonstrate error code 425 if TradeExciseNumber in IE871 document does not match the AfgiftOperatoerPunktAfgiftIdentifikator field.
     */
    @Test
    public void testInvalidTraderExciseNumberInIE817() throws Exception {
        assumeNotNull(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
        // Path to where the IE871 document is located
        String ie871 = "ie871-invalid-TradeExciseNumber.xml";
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        String arc = "22DKTJSJ5HLFU0BAI7164";
        OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
        OIOKvitteringAfvigelseBegrundelseOpretOType response = oioKvitteringAfvigelseBegrundelseOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie871, arc);
        assertTrue(hasError(response.getHovedOplysningerSvar(), 425));
    }


}