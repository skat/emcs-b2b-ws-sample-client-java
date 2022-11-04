package dk.skat.emcs.b2b.sample.process;

import dk.skat.emcs.b2b.sample.*;
import org.junit.Test;

import java.io.File;

public class ProcessIE815AndIE810 extends BaseClientTest {

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


}
