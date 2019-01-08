package dk.skat.emcs.b2b.sample;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.*;
//import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentService;
//import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentServicePortType;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentServicePortType;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;


/**
 * OIOLedsageDokumentAnnulleringSamlingHentClient

 * @author SKAT
 * @since 1.0
 * */

public class OIOLedsageDokumentAnnulleringSamlingHentClient extends EMCSBaseClient {
    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentAnnulleringSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */

    private OIOLedsageDokumentAnnulleringSamlingHentClient() {
    }
    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentAnnulleringSamlingHent service
     */
    public OIOLedsageDokumentAnnulleringSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentAnnulleringSamlingHent service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */

    public String  invoke(String virksomhedSENummerIdentifikator,
                          String afgiftOperatoerPunktAfgiftIdentifikator,
                          Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(interval);



        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    public String  invoke(String virksomhedSENummerIdentifikator,
                          String afgiftOperatoerPunktAfgiftIdentifikator,
                          String  arcnumber)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(arcnumber);



        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    private String invokeit(String virksomhedSENummerIdentifikator,
                           String afgiftOperatoerPunktAfgiftIdentifikator,
                           SøgeParametreStrukturType søgeParametreStrukturType
                           )
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        final String newLine = System.getProperty("line.separator");

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

        OIOLedsageDokumentAnnulleringSamlingHentIType oioLedsageDokumentAnnulleringSamlingHentIType = new OIOLedsageDokumentAnnulleringSamlingHentIType();

        oioLedsageDokumentAnnulleringSamlingHentIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentAnnulleringSamlingHentIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);

        oioLedsageDokumentAnnulleringSamlingHentIType.setSøgeParametreStruktur(søgeParametreStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentAnnulleringSamlingHentService service = new OIOLedsageDokumentAnnulleringSamlingHentService();
        OIOLedsageDokumentAnnulleringSamlingHentServicePortType port = service.getOIOLedsageDokumentAnnulleringSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();

        sbRequest.append("*******************************************************************").append(newLine);
        sbRequest.append("** HovedOplysninger").append(newLine);
        sbRequest.append("**** Transaction Id: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getHovedOplysninger().getTransaktionIdentifikator()).append(newLine);
        sbRequest.append("**** Transaction Time: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getHovedOplysninger().getTransaktionTid()).append(newLine);
        sbRequest.append("** VirksomhedIdentifikationStruktur").append(newLine);
        sbRequest.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator()).append(newLine);
        sbRequest.append("**** VirksomhedSENummerIdentifikator: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()).append(newLine);
//        sbRequest.append("** Start Date: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getStartDate()).append(newLine);
//        sbRequest.append("** End Date: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getEndDate()).append(newLine);
        sbRequest.append("*******************************************************************").append(newLine);
        LOGGER.info(newLine + sbRequest.toString());


        OIOLedsageDokumentAnnulleringSamlingHentOType out = port.getOIOLedsageDokumentAnnulleringSamlingHent(oioLedsageDokumentAnnulleringSamlingHentIType);

        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(newLine);
        sb.append("** HovedOplysningerSvar").append(newLine);
        sb.append("**** Transaction Id: ").append(out.getHovedOplysningerSvar().getTransaktionIdentifikator()).append(newLine);
        sb.append("**** Transaction Time: ").append(out.getHovedOplysningerSvar().getTransaktionTid()).append(newLine);
        sb.append("**** Service Identification: ").append(out.getHovedOplysningerSvar().getServiceIdentifikator()).append(newLine);
        sb.append("*******************************************************************").append(newLine);
        if (out.getHovedOplysningerSvar().getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : out.getHovedOplysningerSvar().getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    FejlStrukturType fejlStrukturType = (FejlStrukturType) errorOrAdvis;
                    sb.append("**** Error").append(newLine);
                    sb.append("****** Error Code: ").append(fejlStrukturType.getFejlIdentifikator()).append(newLine);
                    sb.append("****** Error Text: ").append(fejlStrukturType.getFejlTekst()).append(newLine);
                }
                if (errorOrAdvis instanceof AdvisStrukturType) {
                    AdvisStrukturType advisStrukturType = (AdvisStrukturType) errorOrAdvis;
                    sb.append("**** Advis").append(newLine);
                    sb.append("****** Advis Code: ").append(advisStrukturType.getAdvisIdentifikator()).append(newLine);
                    sb.append("****** Advis Text: ").append(advisStrukturType.getAdvisTekst()).append(newLine);
                }
            }
        } else {
            sb.append("** IE704 Messages: ").append(newLine);
            List<String> ie704Messages = out.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst();
            if (ie704Messages != null && ie704Messages.size() > 0) {
                for (String message : ie704Messages) {
                    sb.append(message).append(newLine);
                    sb.append("*******************************************************************").append(newLine);
                }
            } else {
                sb.append("There are no IE 704 messages!").append(newLine);
                sb.append("*******************************************************************").append(newLine);
            }
        }

        LOGGER.info(newLine + sb.toString());
        return sb.toString();
    }




}
