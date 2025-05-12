package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertFalse;

/**
 * OIOLedsageDokumentOmdirigeretAdvisSamlingHent Test (IE803 as response)
 * <p>
 * Purpose: Fetch list of IE803s
 * <p>
 * Test case design steps:
 * <p>
 * Step 1: Fetch list of IE803s (using OIOEUReferenceDataAnmod)
 * <p>
 * IMPORTANT: OIOLedsageDokumentOmdirigeretAdvisSamlingHent is also tested as part of {@link OIOLedsageDokumentOpsplitningOpretClientTest#invoke()}
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentOmdirigeretAdvisSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {
        if (getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT) != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK31175143300";
            OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient client = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));
            OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10));
            assertFalse(hasError(response.getHovedOplysningerSvar()));
        }
    }
}