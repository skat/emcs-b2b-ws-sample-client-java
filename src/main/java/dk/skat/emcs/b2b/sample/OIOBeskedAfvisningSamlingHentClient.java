package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOBeskedAfvisningSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOBeskedAfvisningSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * OIOBeskedAfvisningSamlingHentClient
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOBeskedAfvisningSamlingHentClient extends EMCSBaseClient{

    private static final Logger LOGGER = Logger.getLogger(OIOBeskedAfvisningSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOBeskedAfvisningSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOBeskedAfvisningSamlingHent service
     */
    public OIOBeskedAfvisningSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOBeskedAfvisningSamlingHent service
     *
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException   N/A
     * @throws IOException                    N/A
     * @throws SAXException                   N/A
     */

    public OIOBeskedAfvisningSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, interval);


    }

    public OIOBeskedAfvisningSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        System.out.println("afgift = " + afgiftOperatoerPunktAfgiftIdentifikator + ", virksomhed = " + virksomhedSENummerIdentifikator);
        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, 1);
    }

    public String invoke(SamlingHentModel samlingHentModel) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException, JAXBException {
        if (this.endpointURL == null){
            this.endpointURL = getEndpoint("OIOBeskedAfvisningSamlingHent");
        }
        OIOBeskedAfvisningSamlingHentOType result = invokeit(samlingHentModel.getVirksomhedSENummerIdentifikator(), samlingHentModel.getAfgiftOperatoerPunktAfgiftIdentifikator(), 1);
        return SamlingHentMashalling.toString(result,"urn:oio:skat:emcs:ws:1.0.1","OIOBeskedAfvisningSamlingHent_O");
    }

    private OIOBeskedAfvisningSamlingHentOType invokeit(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        OIOBeskedAfvisningSamlingHentIType oioBeskedAfvisningSamlingHentIType = new OIOBeskedAfvisningSamlingHentIType();
        oioBeskedAfvisningSamlingHentIType.setHovedOplysninger(generateHovedOplysningerType());
        oioBeskedAfvisningSamlingHentIType.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        SøgeParametreStrukturType soegeParametreStrukturType = getSøgeParametreStrukturType(interval);
        oioBeskedAfvisningSamlingHentIType.setSøgeParametreStruktur(soegeParametreStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOBeskedAfvisningSamlingHentService service = new OIOBeskedAfvisningSamlingHentService();
        OIOBeskedAfvisningSamlingHentServicePortType port = service.getOIOBeskedAfvisningSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioBeskedAfvisningSamlingHentIType.getHovedOplysninger(),
                oioBeskedAfvisningSamlingHentIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioBeskedAfvisningSamlingHentIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        sbRequest.append("** Start Date: ").append(oioBeskedAfvisningSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getStartDate()).append(NEW_LINE);
        sbRequest.append("** End Date: ").append(oioBeskedAfvisningSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getEndDate()).append(NEW_LINE);
        sbRequest.append("*******************************************************************").append(NEW_LINE);
        LOGGER.info(NEW_LINE + sbRequest.toString());

        System.out.println("user.dir property = " + System.getProperty("user.dir"));
        OIOBeskedAfvisningSamlingHentOType out = port.getOIOBeskedAfvisningSamlingHent(oioBeskedAfvisningSamlingHentIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (!hasError(out.getHovedOplysningerSvar())) {
            sb.append("** IE704 Messages: ").append(NEW_LINE);
            List<String> ie704Messages = out.getBeskedAfvisningSamling().getIE704BeskedTekst();
            if (ie704Messages != null && ie704Messages.size() > 0) {
                for (String message : ie704Messages) {
                    sb.append(prettyFormatDocument(message, 2, true)).append(NEW_LINE);
                    sb.append("*******************************************************************").append(NEW_LINE);
                }
            } else {
                sb.append("There are no IE 704 messages!").append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            }
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }
}
