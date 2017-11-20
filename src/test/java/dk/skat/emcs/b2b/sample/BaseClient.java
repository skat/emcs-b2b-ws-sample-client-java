package dk.skat.emcs.b2b.sample;

/**
 * BaseClient - Helper methods for test clients, e.g. we're using the same VAT number for all tests.
 *
 * @author SKAT
 * @since 1.1
 */
public class BaseClient {

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
