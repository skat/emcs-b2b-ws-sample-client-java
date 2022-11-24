package dk.skat.emcs.b2b.sample;

import org.junit.Test;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

public class ScenarioTest extends BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(ScenarioTest.class.getName());

    /**
     * 1. Call OIOLedsageDokumentOpret (IE815)
     * 2. Call OIOLedsageDokumentAnnulleringOpret (IE810)
     */
    @Test
    public void testCreateIE815AndCancelIE810() throws Exception {
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

        String arc = null;
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);

        String ie810 = "ie810.xml";
        OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
        oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);
    }


    @Test
    public void testCreateIE815andIE818() throws Exception {
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String consignor = getAfgiftOperatoerPunktAfgiftIdentifikator();

        String arc = null;

        String consignee = "DK99025875300";

        // Call OIOLedsageDokumentOpret as Consignor

        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                consignor, ie815);

        if (arc == null) {
            LOGGER.warning("Did not receive ARC number. Exiting");
            return;
        }
        LOGGER.info("Received ARC = " + arc);

        LOGGER.info("Waiting 2 minutes before proceeding...");
        Thread.sleep(1000*60*2);

        // Call OIOLedsageDokumentSamlingHent as Consignee
        OIOLedsageDokumentSamlingHentClient ledsageDokumentSamlingHentClient = new OIOLedsageDokumentSamlingHentClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT));
        ledsageDokumentSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                consignee, arc);

        // Call OIOKvitteringOpret as Consignee

        File ie818 = new File ("ie818.xml");
        OIOKvitteringOpretClient oioKvitteringOpretClient = new OIOKvitteringOpretClient(getEndpoint(OIO_KVITTERING_OPRET));
        oioKvitteringOpretClient.invoke(virksomhedSENummerIdentifikator,
                consignee,ie818, arc);

        LOGGER.info("Waiting 2 minutes before proceeding...");
        Thread.sleep(1000*60*2);

        // Call OIOKvitteringSamlingHent as Consignee

        OIOKvitteringSamlingHentClient client = new OIOKvitteringSamlingHentClient(getEndpoint(OIO_KVITTERING_SAMLIMG_HENT));
        client.invoke(virksomhedSENummerIdentifikator,
                consignee, arc);

    }

    /**
     * 1. Call OIOLedsageDokumentOpret (IE815)
     * 2. Call OIOLedsageDokumentAnnulleringOpret (IE810)
     * 3. Call OIOLedsageDokumentAnnulleringOpret (same IE810 as in step 2.) to trigger IE704
     * 4. Call OIOBeskedAfvisningSamlingHent to get IE704
     */
    @Test
    public void testCreateIE815AndCancelIE810AndTriggerIE704() throws Exception {
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

        String arc = null;
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);

        String ie810 = "ie810.xml";
        OIOLedsageDokumentAnnulleringOpretClient oioLedsageDocumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(getEndpoint(OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET));
        oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);

        // Now trigger 704
        oioLedsageDocumentAnnulleringOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie810, arc);

        // Get the 704
        OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(getEndpoint(OIO_BESKED_AFVISNING_SAMLING_HENT));
        oioBeskedAfvisningSamlingHentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, 1);

    }

    /**
     * 1. Call OIOLedsageDokumentOpret (IE815)
     * 2. Call IOLedsageDokumentNotifikationOpret (IE819)
     */
    @Test
    public void testCreateIE815AndNotifyIE918() throws Exception {
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = getAfgiftOperatoerPunktAfgiftIdentifikator();

        String arc = null;
        File ie815 = new File("ie815.xml");
        OIOLedsageDokumentOpretClient oioLedsageDocumentClient = new OIOLedsageDokumentOpretClient(getEndpoint(OIO_LEDSAGEDOCUMENT_OPRET));
        arc = oioLedsageDocumentClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie815);

        String ie819 = "ie819.xml";
        OIOLedsageDokumentNotifikationOpretClient oioLedsageDokumentNotifikationOpretClient = new OIOLedsageDokumentNotifikationOpretClient(getEndpoint(OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET));
        oioLedsageDokumentNotifikationOpretClient.invoke(virksomhedSENummerIdentifikator,
                afgiftOperatoerPunktAfgiftIdentifikator, ie819, arc);
    }

    @Test
    public void testOIOEUReferenceData() throws Exception {

        final String beskedIdentifikator = UUID.randomUUID().toString();
        // VAT Number of the entity sending. Rule of thumb: this number matches
        // this CVR number present in the certificate.
        String virksomhedSENummerIdentifikator = getVirksomhedSENummerIdentifikator();
        // Excise number
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82070486100";
        {
            OIOEUReferenceDataAnmodClient client = new OIOEUReferenceDataAnmodClient(getEndpoint(OIO_EUREFERENCE_DATA_ANMOD));
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, "ie705.xml", beskedIdentifikator);
        }
        {
            OIOEUReferenceDataHentClient client = new OIOEUReferenceDataHentClient(getEndpoint(OIO_EUREFERENCE_DATA_HENT));
            client.invoke(virksomhedSENummerIdentifikator,
                    afgiftOperatoerPunktAfgiftIdentifikator, beskedIdentifikator);
        }


    }


}
