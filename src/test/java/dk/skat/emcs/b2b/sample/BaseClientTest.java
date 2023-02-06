package dk.skat.emcs.b2b.sample;

/**
 * BaseClient - Helper methods for test clients, e.g. we're using the same VAT number for all tests.
 *
 * @author SKAT
 * @since 1.2
 */
public class BaseClientTest {

    protected static final String OIO_PAAMINDELSE_SAMLING_HENT = "OIOPaamindelseSamlingHent";
    protected static final String OIO_EUREFERENCE_DATA_ANMOD = "OIOEUReferenceDataAnmod";
    protected static final String OIO_EUREFERENCE_DATA_HENT = "OIOEUReferenceDataHent";
    protected static final String OIO_LEDSAGEDOCUMENT_OPRET = "OIOLedsageDocumentOpret";
    protected static final String OIO_KVITTERING_OPRET = "OIOKvitteringOpret";
    protected static final String OIO_KVITTERING_SAMLIMG_HENT = "OIOKvitteringSamlingHent";
    protected static final String OIO_LEDSAGEDOKUMENT_ANNULLERING_OPRET = "OIOLedsageDokumentAnnulleringOpret";
    protected static final String OIO_BESKED_AFVISNING_SAMLING_HENT = "OIOBeskedAfvisningSamlingHent";
    protected static final String OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_OPRET = "OIOLedsageDokumentNotifikationOpret";
    protected static final String OIO_LEDSAGE_DOKUMENT_SAMLLING_HENT = "OIOLedsageDokumentSamlingHent";
    protected static final String OIO_LEDSAGE_DOKUMENT_OPSPLITNING_OPRET = "OIOLedsageDokumentOpsplitningOpret";
    protected static final String OIO_LEDSAGE_DOKUMENT_ANNULLERING_SAMLING_HENT = "OIOLedsageDokumentAnnulleringSamlingHent";
    protected static final String OIO_LEDSAGE_DOKUMENT_OMDIRIGERET_ADVIS_SAMLING_HENT = "OIOLedsageDokumentOmdirigeretAdvisSamlingHent";
    protected static final String OIO_FORSENDELSE_AFBRYDELSE_BESKED_SAMLING_HENT = "OIOForsendelseAfbrydelseBeskedSamlingHent";



    static {
        // Comment out for running tests in IDE and change "CHANGEME" values.
        // System.setProperty("dk.skat.emcs.b2b.sample.P12_PASSPHRASE", "CHANGEME");
        // System.setProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT", "CHANGEME");
    }

    /**
     * Get VAT number
     *
     * VAT Number of the entity sending. Rule of thumb: this number matches this CVR number present in the certificate.
     *
     * @return VAT number
     */
    protected String getVirksomhedSENummerIdentifikator() {
        String virksomhedSENummerIdentifikator = "30808460";
        return virksomhedSENummerIdentifikator;
    }

    /**
     * Get AfgiftOperatoerPunktAfgiftIdentifikator
     *
     * @return AfgiftOperatoerPunktAfgiftIdentifikator
     */
    protected String getAfgiftOperatoerPunktAfgiftIdentifikator() {
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";
        return afgiftOperatoerPunktAfgiftIdentifikator;
    }

    /**
     * Get endpoint
     *
     * @return Endpoint
     */
    protected String getEndpoint(String service) {
        String endpointURL =
                System.getProperty("dk.skat.emcs.b2b.sample." + service + ".ENDPOINT");
        if (endpointURL == null) {
            System.out.println(service + ": Endpoint not provided, skipping test");
        }
        return endpointURL;
    }

    protected void sleep(int minutes) throws InterruptedException {
        Thread.sleep(1000 * 60 * minutes);
    }

}
