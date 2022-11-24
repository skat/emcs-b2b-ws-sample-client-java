package dk.skat.emcs.b2b.sample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.UUID;

public class OIOEUReferenceDataAnmodClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {
        String endpointURL =
                getEndpoint("OIOEUReferenceDataAnmod");

        if (endpointURL != null) {
            // VAT Number of the entity sending. Rule of thumb: this number matches
            // this CVR number present in the certificate.
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            // Excise number
            String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
            final String beskedIdentifikator = UUID.randomUUID().toString();
            OIOEUReferenceDataAnmodClient client = new OIOEUReferenceDataAnmodClient(endpointURL);
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
        }
    }
}