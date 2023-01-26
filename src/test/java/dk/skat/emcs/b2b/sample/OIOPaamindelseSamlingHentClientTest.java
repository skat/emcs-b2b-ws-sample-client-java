package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

public class OIOPaamindelseSamlingHentClientTest extends BaseClientTest {

    @Test
    public void invoke() throws DatatypeConfigurationException {

        if (getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT) != null) {
            String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
            String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
            OIOPaamindelseSamlingHentClient client = new OIOPaamindelseSamlingHentClient(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
            client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator);
        }
    }
}