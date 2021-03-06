package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentSamlingHentServicePortType;
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
 * OIOLedsageDokumentSamlingHentClient
 *
 *
 * @author SKAT
 * @since 1.2
 */
public class OIOLedsageDokumentSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentSamlingHent service
     */
    public OIOLedsageDokumentSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentSamlingHent service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ARCnummer ARC number
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public String invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ARCnummer) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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

        OIOLedsageDokumentSamlingHentIType oioLedsageDokumentSamlingHentIType = new OIOLedsageDokumentSamlingHentIType();
        oioLedsageDokumentSamlingHentIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentSamlingHentIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        soegeParametre.setLedsagedokumentARCIdentifikator(ARCnummer);
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        oioLedsageDokumentSamlingHentIType.setSøgeParametreStruktur(soegeParametreStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentSamlingHentService service = new OIOLedsageDokumentSamlingHentService();
        OIOLedsageDokumentSamlingHentServicePortType port = service.getOIOLedsageDokumentSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentSamlingHentIType.getHovedOplysninger(),
                oioLedsageDokumentSamlingHentIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentSamlingHentIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        sbRequest.append("** LedsagedokumentARCIdentifikator: ").append(oioLedsageDokumentSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getLedsagedokumentARCIdentifikator()).append(NEW_LINE);
        sbRequest.append("*******************************************************************").append(NEW_LINE);
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOLedsageDokumentSamlingHentOType out = port.getOIOLedsageDokumentSamlingHent(oioLedsageDokumentSamlingHentIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (!hasError(out.getHovedOplysningerSvar())) {
            sb.append("** IE801 Messages: ").append(NEW_LINE);
            List<String> ie801Messages = out.getLedsageDokumentStamDataSamling().getIE801BeskedTekst();
            for (String message : ie801Messages) {
                sb.append(message).append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            }
        }

        LOGGER.info(NEW_LINE + sb.toString());
        return sb.toString();
    }

}
