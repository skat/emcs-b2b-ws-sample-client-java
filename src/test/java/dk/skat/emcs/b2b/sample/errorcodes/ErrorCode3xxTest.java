package dk.skat.emcs.b2b.sample.errorcodes;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerSvarType;
import dk.skat.emcs.b2b.sample.*;
import oio.skat.emcs.ws._1_0.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

/**
 * ErrorCode3xxTest
 *
 * @author UFST
 */
public class ErrorCode3xxTest extends BaseClientTest {

    @Before
    public void setUp() {
        boolean addGetClients = true;
        boolean addSubmitClients = true;
        SøgeParametreStrukturType spst = SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(10);
        final String fakeARC = "25DKSECURITYTESTING00";
        // Get document services (*SamlingHent and *DataHent)
        if (addGetClients) {
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
                OIOBeskedAfvisningSamlingHentClient client = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator).getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
                OIOEUReferenceDataHentClient client = new OIOEUReferenceDataHentClient(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, UUID.randomUUID().toString()).getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
                OIOEksportAfvisningSamlingHentClient client = new OIOEksportAfvisningSamlingHentClient(getEndpoint(OIO_EKSPORT_AFVISNING_SAMLING_HENT));
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator).getHovedOplysningerSvar();
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
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, fakeARC).getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
                OIOLedsageDokumentAnnulleringSamlingHentClient client = new OIOLedsageDokumentAnnulleringSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT));
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, 1).getHovedOplysningerSvar();
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
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, fakeARC).getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
                OIOPaamindelseSamlingHentClient client = new OIOPaamindelseSamlingHentClient(getEndpoint(OIO_PAAMINDELSE_SAMLING_HENT));
                return client.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator).getHovedOplysningerSvar();
            });
        }
        // Submit document services (*Opret and *Anmod)
        if (addSubmitClients) {
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
                final String beskedIdentifikator = UUID.randomUUID().toString();
                OIOEUReferenceDataAnmodClient client = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
                OIOEUReferenceDataAnmodOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
                String ie837 = "ie837.xml";
                OIOForsinkelseForklaringOpretClient client = new OIOForsinkelseForklaringOpretClient(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
                OIOForsinkelseForklaringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie837, fakeARC, afgiftOperatoerPunktAfgiftIdentifikator);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
                String ie871 = "ie871.xml";
                OIOKvitteringAfvigelseBegrundelseOpretClient client = new OIOKvitteringAfvigelseBegrundelseOpretClient(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
                OIOKvitteringAfvigelseBegrundelseOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie871, fakeARC);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
                File ie818 = new File("ie818.xml");
                OIOKvitteringOpretClient client = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
                OIOKvitteringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie818, fakeARC);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
                String ie810 = "ie810.xml";
                OIOLedsageDokumentAnnulleringOpretClient client = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
                OIOLedsageDokumentAnnulleringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie810, fakeARC);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
                File ie815 = new File("ie815.xml");
                OIOLedsageDokumentOpretClient client = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
                OIOLedsageDokumentOpretOType response = client.invoke2(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie815);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
                String ie813 = "ie813.xml";
                OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
                OIOLedsageDokumentDestinationSkiftOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie813, fakeARC);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
                String ie819 = "ie819.xml";
                OIOLedsageDokumentNotifikationOpretClient client = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
                OIOLedsageDokumentNotifikationOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie819, fakeARC);
                return response.getHovedOplysningerSvar();
            });
            clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
                assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
                String ie825 = "split-ie825.xml";
                OIOLedsageDokumentOpsplitningOpretClient client = new OIOLedsageDokumentOpsplitningOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
                OIOLedsageDokumentOpsplitningOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                        afgiftOperatoerPunktAfgiftIdentifikator, ie825, fakeARC);
                return response.getHovedOplysningerSvar();
            });
        }
        System.out.println("Generated " + clients.size() + " clients");
    }

    @Test
    public void testClientList() {
        assertEquals(23, clients.size());
    }

    // EXCISE NUMBER DK19552101100 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), "DK19552101100"), 302));
        }
    }

    // EXCISE NUMBER DK19552101500 -> CVR 19552101 / SE 19552101
    @Test
    public void testNoDelegationAccess500() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), "DK19552101500"), 302));
        }
    }

    // EXCISE NUMBER DK19552101300 -> CVR 19552101 / SE 30808460
    @Test
    public void testNoDelegationSENotRelatedToCVR() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall("30808460", "DK19552101300"), 300));
        }
    }

    // EXCISE NUMBER DK3117514300 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode102WrongExciseNumberFormat() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), "DK3117514300"), 102));
        }
    }

    // EXCISE NUMBER DK31175143999 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode301WrongExciseNumberSuffixCode() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), "DK31175143999"), 301));
        }
    }

    // EXCISE NUMBER DK99025875600 -> CVR 19552101 / SE 31038421
    @Test
    public void testErrorCode302ExciseNumberHasNotDelegatedToSENumberNotEqualsCVR() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall("31038421", getAfgiftOperatoerPunktAfgiftIdentifikator()), 302));
        }
    }

    // EXCISE NUMBER DK30808460300 -> CVR 19552101 / SE 19552101
    @Test
    public void testErrorCode302ExciseNumberHasNotDelegatedToSENumberEqualsCVR() throws Exception {
        for (ErrorCodeClientTest errorCodeClientTest : clients) {
            assertTrue(hasError(errorCodeClientTest.doCall(getVirksomhedSENummerIdentifikator(), "DK30808460300"), 302));
        }
    }

    private List<ErrorCodeClientTest> clients = new LinkedList<>();

    interface ErrorCodeClientTest {
        HovedOplysningerSvarType doCall(String virksomhedSENummerIdentifikator, String afgiftOperatoerPunktAfgiftIdentifikator) throws Exception;
    }
}