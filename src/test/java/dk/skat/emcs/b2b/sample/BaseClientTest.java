package dk.skat.emcs.b2b.sample;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerSvarType;
import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * BaseClient - Helper methods for test clients, e.g. we're using the same VAT number for all tests.
 *
 * @author SKAT
 * @since 1.2
 */
public class BaseClientTest {

    private static final Logger LOGGER = Logger.getLogger(BaseClientTest.class.getName());

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
    protected static final String OIO_FORSINKELSE_FORKLARING_OPRET = "OIOForsinkelseForklaringOpret";
    protected static final String OIO_EKSPORT_AFVISNING_SAMLING_HENT = "OIOEksportAfvisningSamlingHent";
    protected static final String OIO_EKSPORT_ANGIVELSE_INVALIDERING_NOTIFIKATION_SAMLING_HENT = "OIOEksportAngivelseInvalideringNotifikationSamlingHent";
    protected static final String OIO_EKSPORT_GODKENDELSE_SAMLING_HENT = "OIOEksportGodkendelseSamlingHent";
    protected static final String OIO_HAENDELSE_RAPPORT_SAMLING_HENT = "OIOHaendelseRapportSamlingHent";
    protected static final String OIO_LEDSAGE_DOKUMENT_DESTINATION_SKIFT_SAMLING_HENT = "OIOLedsageDokumentDestinationSkiftSamlingHent";
    protected static final String OIO_LEDSAGE_DOKUMENT_NOTIFIKATION_SAMLING_HENT = "OIOLedsageDokumentNotifikationSamlingHent";

    /**
     * Get VAT number
     *
     * VAT Number of the entity sending. Rule of thumb: this number matches this CVR number present in the certificate.
     *
     * @return VAT number
     */
    protected String getVirksomhedSENummerIdentifikator() {
        String virksomhedSENummerIdentifikator = "19552101";
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
        String key = "dk.skat.emcs.b2b.sample." + service + ".ENDPOINT";
        Config conf = getConfig();
        if (!conf.hasPath(key)) {
            return null;
        }

        // if 'app.conf' does not exist TypeSafa config will try out the -D param
        String endpointURL = getConfig().getString(key);
        if (endpointURL == null) {
            System.out.println(service + ": Endpoint not provided, skipping test");
        }
        return endpointURL;
    }

    protected void sleep(int minutes) throws InterruptedException {
        LOGGER.info("Sleeping " + minutes + " minutes.");
        for (int i = 1; i <= minutes; i++) {
            LOGGER.info((minutes + 1) - i + " minute(s) remaining...");
            Thread.sleep(1000 * 60);
        }
        LOGGER.info("Awake.");
    }

    protected static Config getConfig() {
        return ConfigFactory.parseFile(new File("app.conf")).withFallback(ConfigFactory.load()).resolve();
    }

    static {
        String alias = System.getProperty("dk.skat.emcs.b2b.sample.ClientCertAlias");
        if (alias == null) {
            // Pick default alias
            alias = "dinovinoimport_system_integrationstest_s1";
            System.setProperty("dk.skat.emcs.b2b.sample.ClientCertAlias", alias);
        }

        System.out.println("Running with alias: " + alias);
    }

    protected boolean hasError(HovedOplysningerSvarType hovedOplysningerSvarType, int errorCode) {
        boolean result = false;
        if (hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    FejlStrukturType fejlStrukturType = (FejlStrukturType) errorOrAdvis;
                    if (errorCode == fejlStrukturType.getFejlIdentifikator().intValue()) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    protected boolean hasError(HovedOplysningerSvarType hovedOplysningerSvarType) {
        boolean result = false;
        if (hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    protected boolean hasAdvis(HovedOplysningerSvarType hovedOplysningerSvarType, int advisCode) {
        boolean result = false;
        if (!hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur().isEmpty()) {
            for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof AdvisStrukturType) {
                    AdvisStrukturType advisStrukturType = (AdvisStrukturType) errorOrAdvis;
                    if (advisCode == advisStrukturType.getAdvisIdentifikator().intValue()) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Generate a search with start and end date im the future
     * @return
     */
    protected SøgeParametreStrukturType getSearchPeriodInFuture() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,6);
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH,1);
        Date endDate = cal.getTime();
        return  SøgeParametreStrukturTypeHelper.getSøgeParametreStrukturType(startDate, endDate);
    }

}
