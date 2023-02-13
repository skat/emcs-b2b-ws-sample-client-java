package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * OIOPaamindelseSamlingHent Test
 *
 * This client depends on the Consignee NOT submitting the required IE818 within the deadline from the time the
 * Consignee received the IE810. This is currently 5 days as explained here: https://skat.dk/data.aspx?oid=2244647
 *
 * As such it is very not practical to do a programmatic test that sleeps for 5 days and then calls OIOPaamindelseSamlingHent.
 *
 * To test this OIOPaamindelseSamlingHent follow these steps:
 *
 * Step 1: Submit IE815 using {@link OIOLedsageDocumentOpretClientTest#invoke()} and do NOT submit the corresponding IE818
 * Step 2: Wait 6 days (to be sure)
 * Step 3: Invoke the OIOPaamindelseSamlingHent service (this test)
 *
 * @author SKAT
 * @since 1.2
 */
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