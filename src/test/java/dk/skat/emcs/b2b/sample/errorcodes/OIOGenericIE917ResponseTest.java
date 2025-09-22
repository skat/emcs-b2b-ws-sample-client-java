package dk.skat.emcs.b2b.sample.errorcodes;

import dk.skat.emcs.b2b.sample.*;
import oio.skat.emcs.ws._1_0.*;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeNotNull;

/**
 * OIOGenericIE917ResponseTest
 *
 * Test that sends invalid document to all OIO*Opret services and checks that
 * the response contains a value in the IE917BeskedTekst field.
 *
 * @author UFST
 */
public class OIOGenericIE917ResponseTest extends BaseClientTest {

    private void setUp() {
        clients.clear();
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
            String ie837 = "ie837-triggers-ie917-response.xml";
            OIOForsinkelseForklaringOpretClient client = new OIOForsinkelseForklaringOpretClient(getEndpoint(OIO_FORSINKELSE_FORKLARING_OPRET));
            OIOForsinkelseForklaringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie837, "dummy", afgiftOperatoerPunktAfgiftIdentifikator);
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
            String ie871 = "ie871-triggers-ie917-response.xml";
            OIOKvitteringAfvigelseBegrundelseOpretClient client = new OIOKvitteringAfvigelseBegrundelseOpretClient(getEndpoint(OIO_KVITTERING_AFVIGELSE_BEGRUNDELSE_OPRET));
            OIOKvitteringAfvigelseBegrundelseOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie871, "dummy");
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_KVITTERING_OPRET));
            File ie818 = new File ("ie818-triggers-ie917-response.xml");
            OIOKvitteringOpretClient client = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
            OIOKvitteringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator,ie818, "dummy");
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
            String ie810 = "ie810-triggers-ie917-response.xml";
            OIOLedsageDokumentAnnulleringOpretClient client = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
            OIOLedsageDokumentAnnulleringOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie810, "dummy");
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            File ie815 = new File("ie815-triggers-ie917-response.xml");
            OIOLedsageDokumentOpretClient client = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
            OIOLedsageDokumentOpretOType response = client.invoke2(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie815);
            return response.getOutput().getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
            String ie813 = "ie813-triggers-ie917-response.xml";
            OIOLedsageDokumentDestinationSkiftOpretClient client = new OIOLedsageDokumentDestinationSkiftOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_OPRET));
            OIOLedsageDokumentDestinationSkiftOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie813, "dummy");
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
            String ie819 = "ie819-triggers-ie917-response.xml";
            OIOLedsageDokumentNotifikationOpretClient client = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
            OIOLedsageDokumentNotifikationOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie819, "dummy");
            return response.getIE917BeskedTekst().isEmpty();
        });
        clients.add((virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator) -> {
            assumeNotNull(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
            String ie825 = "ie825-triggers-ie917-response.xml";
            OIOLedsageDokumentOpsplitningOpretClient client = new OIOLedsageDokumentOpsplitningOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET));
            OIOLedsageDokumentOpsplitningOpretOType response = client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, ie825, "dummy");
            return response.getOutput().getIE917BeskedTekst().isEmpty();
        });
        System.out.println("Generated " + clients.size() + " clients");
    }


    @Test
    public void triggerIE917() throws Exception {
        setUp();
        for (IE917ClientTest ie917ClientTest : clients) {
            assertFalse(ie917ClientTest.doCall(getVirksomhedSENummerIdentifikator(), getAfgiftOperatoerPunktAfgiftIdentifikator()));
        }
    }


    private List<IE917ClientTest> clients = new LinkedList<>();

    interface IE917ClientTest {
        boolean doCall(String virksomhedSENummerIdentifikator, String afgiftOperatoerPunktAfgiftIdentifikator) throws Exception;
    }
}