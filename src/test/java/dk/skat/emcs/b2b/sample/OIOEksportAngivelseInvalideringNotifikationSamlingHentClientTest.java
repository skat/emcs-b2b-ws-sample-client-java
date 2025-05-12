package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEksportAngivelseInvalideringNotifikationSamlingHentOType;
import org.junit.Test;

import static dk.skat.emcs.b2b.sample.SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOEksportAngivelseInvalideringNotifikationSamlingHent Test
 */
public class OIOEksportAngivelseInvalideringNotifikationSamlingHentClientTest extends BaseClientTest {

    @Test
    public void searchLastNumberOfMonths() throws Exception {
        assumeNotNull(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOEksportAngivelseInvalideringNotifikationSamlingHentClient client = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        OIOEksportAngivelseInvalideringNotifikationSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSearchPeriodLastNMonths(24));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getEksportAngivelseInvalideringNotifikationListe().getIE836BeskedTekst().isEmpty());
    }

    @Test
    public void searchByDateRange() throws Exception {
        assumeNotNull(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOEksportAngivelseInvalideringNotifikationSamlingHentClient client = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        OIOEksportAngivelseInvalideringNotifikationSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType("2023-11-01", "2023-12-01"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getEksportAngivelseInvalideringNotifikationListe().getIE836BeskedTekst().isEmpty());
    }

    @Test
    public void searchByARC() throws Exception {
        assumeNotNull(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();
        OIOEksportAngivelseInvalideringNotifikationSamlingHentClient client = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
        OIOEksportAngivelseInvalideringNotifikationSamlingHentOType response = client.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator,
                getSøgeParametreStrukturType("23DK54AOQCEVNRX5XNT15"));
        assertFalse(hasError(response.getHovedOplysningerSvar()));
        assertFalse(response.getEksportAngivelseInvalideringNotifikationListe().getIE836BeskedTekst().isEmpty());
    }
}
