package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOPåmindelseSamlingHentOType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

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
 * Step 1: Submit IE815 using {@link OIOLedsageDokumentOpretClientTest#invoke()} and do NOT submit the corresponding IE818
 * Step 2: Wait 6 days (to be sure)
 * Step 3: Invoke the OIOPaamindelseSamlingHent service (this test)
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOPaamindelseSamlingHentClientTest extends BaseClientTest {

    @Test
    public void searchByDateRange() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
        OIOPaamindelseSamlingHentClient client = new OIOPaamindelseSamlingHentClient(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
        OIOPåmindelseSamlingHentOType response = client.invoke(
                getVirksomhedSENummerIdentifikator(),
                getAfgiftOperatoerPunktAfgiftIdentifikator(),
                getSøgeParametreStrukturType("2025-04-01", "2025-05-01"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getPåmindelseSamling().getIE802BeskedTekst().isEmpty());
    }

    @Test
    public void searchByARC() throws DatatypeConfigurationException {
        assumeNotNull(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
        OIOPaamindelseSamlingHentClient client = new OIOPaamindelseSamlingHentClient(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
        OIOPåmindelseSamlingHentOType response = client.invoke(
                getVirksomhedSENummerIdentifikator(),
                getAfgiftOperatoerPunktAfgiftIdentifikator(),
                getSøgeParametreStrukturType("25DKWEBLDTMGNKR6T2E18"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getPåmindelseSamling().getIE802BeskedTekst().isEmpty());
    }

}