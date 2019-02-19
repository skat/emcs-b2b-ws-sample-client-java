package dk.skat.emcs.b2b.sample;

/**
 * BaseClient - Helper methods for test clients, e.g. we're using the same VAT number for all tests.
 *
 * @author SKAT
 * @since 1.2
 */
public class BaseClient {

    static {
        // Comment out for running tests in IDE and change "CHANGEME" values.
        // System.setProperty("dk.skat.emcs.b2b.sample.P12_PASSPHRASE", "CHANGEME");
        // System.setProperty("dk.skat.emcs.b2b.sample.OIOLedsageDocumentOpret.ENDPOINT", "CHANGEME");
    }

    /**
     * Get VAT number
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

}
