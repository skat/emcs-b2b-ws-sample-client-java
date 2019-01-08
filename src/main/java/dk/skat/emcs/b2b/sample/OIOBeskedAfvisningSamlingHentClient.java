package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOBeskedAfvisningSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOBeskedAfvisningSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * OIOBeskedAfvisningSamlingHentClient
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOBeskedAfvisningSamlingHentClient extends EMCSBaseClient {

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

    public String invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, interval);


    }

    public String invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, 1);


    }

    private String invokeit(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        // Generate Transaction Id
        final String transactionID = TransactionIdGenerator.getTransactionId();

        // Generate Transaction Time
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        XMLGregorianCalendar transactionTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

        // Build HovedOplysninger Object and set transaction id and time
        HovedOplysningerType hovedOplysningerType = new HovedOplysningerType();
        hovedOplysningerType.setTransaktionIdentifikator(transactionID);
        hovedOplysningerType.setTransaktionTid(transactionTime);

        // Build VirksomhedIdentifikationStruktur
        VirksomhedIdentifikationStrukturType virksomhedIdentifikationStrukturType = new VirksomhedIdentifikationStrukturType();
        virksomhedIdentifikationStrukturType.setAfgiftOperatoerPunktAfgiftIdentifikator(afgiftOperatoerPunktAfgiftIdentifikator);
        VirksomhedIdentifikationStrukturType.Indberetter indberetter = new VirksomhedIdentifikationStrukturType.Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);

        OIOBeskedAfvisningSamlingHentIType oioBeskedAfvisningSamlingHentIType = new OIOBeskedAfvisningSamlingHentIType();
        oioBeskedAfvisningSamlingHentIType.setHovedOplysninger(hovedOplysningerType);
        oioBeskedAfvisningSamlingHentIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
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

        OIOBeskedAfvisningSamlingHentOType out = port.getOIOBeskedAfvisningSamlingHent(oioBeskedAfvisningSamlingHentIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (!hasError(out.getHovedOplysningerSvar())) {
            sb.append("** IE704 Messages: ").append(NEW_LINE);
            List<String> ie704Messages = out.getBeskedAfvisningSamling().getIE704BeskedTekst();
            if (ie704Messages != null && ie704Messages.size() > 0) {
                for (String message : ie704Messages) {
                    sb.append(message).append(NEW_LINE);
                    sb.append("*******************************************************************").append(NEW_LINE);
                }
            } else {
                sb.append("There are no IE 704 messages!").append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            }
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return sb.toString();
    }

}
