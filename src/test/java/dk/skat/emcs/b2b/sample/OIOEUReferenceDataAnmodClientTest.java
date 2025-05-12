package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEUReferenceDataAnmodOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOEUReferenceDataAnmod Test (IE705 as request)
 * <p>
 * Purpose: Verify submit of IE705.
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Submit IE705 (using OIOEUReferenceDataAnmod)
 * <p>
 * IMPORTANT: OIOEUReferenceDataHent is also tested as part of {@link OIOEUReferenceDataHentClientTest#scenario()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOEUReferenceDataAnmodClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {
        assumeNotNull(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
        final String beskedIdentifikator = UUID.randomUUID().toString();
        OIOEUReferenceDataAnmodClient client = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
        OIOEUReferenceDataAnmodOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
        assertFalse(hasError(response.getHovedOplysningerSvar()));
    }
}