package dk.skat.emcs.b2b.sample.errorcodes;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerSvarType;
import dk.skat.emcs.b2b.sample.*;
import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOGenericSamlingHentClientTest
 *
 * Test error code 130 for services that get documents (*SamlingHent)
 *
 * @author UFST
 */
public class OIOGenericSamlingHentClientTest extends BaseClientTest {

    private void setUp(SøgeParametreStrukturType spst) {
        clients.clear();
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
            OIOBeskedAfvisningSamlingHentClient client = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
            OIOEksportAfvisningSamlingHentClient client = new OIOEksportAfvisningSamlingHentClient(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
            OIOEksportAngivelseInvalideringNotifikationSamlingHentClient client = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(getEndpoint(OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_EKSPORT_GODKENDELSE_SAMLING_HENT));
            OIOEksportGodkendelseSamlingHentClient client = new OIOEksportGodkendelseSamlingHentClient(getEndpoint(OIO_EKSPORT_GODKENDELSE_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT));
            OIOForsendelseAfbrydelseBeskedSamlingHentClient client = new OIOForsendelseAfbrydelseBeskedSamlingHentClient(getEndpoint(OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_HAENDELSE_RAPPORT_SAMLING_HENT));
            OIOHaendelseRapportSamlingHentClient client = new OIOHaendelseRapportSamlingHentClient(getEndpoint(OIO_HAENDELSE_RAPPORT_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
            OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
            OIOLedsageDokumentAnnulleringSamlingHentClient client = new OIOLedsageDokumentAnnulleringSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT));
            OIOLedsageDokumentDestinationSkiftSamlingHentClient client = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));
            OIOLedsageDokumentNotifikationSamlingHentClient client = new OIOLedsageDokumentNotifikationSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));
            OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient client = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
            OIOLedsageDokumentSamlingHentClient client = new OIOLedsageDokumentSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
            OIOPaamindelseSamlingHentClient client = new OIOPaamindelseSamlingHentClient(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
            return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, spst).getHovedOplysningerSvar();
        });


        System.out.println("Generated " + clients.size() + " clients");
    }


    @Test
    public void testUnknownARC() throws Exception { // 1
        SøgeParametreStrukturType spst = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre sp = new SøgeParametreStrukturType.SøgeParametre();
        sp.setLedsagedokumentARCIdentifikator("95DKWEBLDTMGNKR6T2E18");
        spst.setSøgeParametre(sp);
        setUp(spst);
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            HovedOplysningerSvarType hos = errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator());
            assertTrue(hasAdvis(hos, 130));
        }
    }

    @Test
    public void testSearchInFuture() throws Exception {
        SøgeParametreStrukturType spst = getSearchPeriodInFuture();
        setUp(spst);
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            HovedOplysningerSvarType hos = errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator());
            assertTrue(hasAdvis(hos, 130));
        }
    }

    private List<ErrorCodeClientTest> clients = new LinkedList<>();

    interface ErrorCodeClientTest {
        HovedOplysningerSvarType doCall(String virksomhedSENummerIdentifikator, String afgiftOperatoerPunktAfgiftIdentifikator) throws Exception;
    }
}