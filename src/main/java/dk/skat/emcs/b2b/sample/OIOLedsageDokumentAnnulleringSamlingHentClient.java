package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
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
 * OIOLedsageDokumentAnnulleringSamlingHentClient
 *
 * @author SKAT
 * @since 1.0
 */
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
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException   N/A
     * @throws IOException                    N/A
     * @throws SAXException                   N/A
     */

    OIOLedsageDokumentAnnulleringSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(interval);


        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    public OIOLedsageDokumentAnnulleringSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         String arcnumber)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(arcnumber);
        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    private OIOLedsageDokumentAnnulleringSamlingHentOType invokeit(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            SøgeParametreStrukturType søgeParametreStrukturType
    )
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

        OIOLedsageDokumentAnnulleringSamlingHentIType oioLedsageDokumentAnnulleringSamlingHentIType = new OIOLedsageDokumentAnnulleringSamlingHentIType();

        oioLedsageDokumentAnnulleringSamlingHentIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentAnnulleringSamlingHentIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);

        oioLedsageDokumentAnnulleringSamlingHentIType.setSøgeParametreStruktur(søgeParametreStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentAnnulleringSamlingHentService service = new OIOLedsageDokumentAnnulleringSamlingHentService();
        OIOLedsageDokumentAnnulleringSamlingHentServicePortType port = service.getOIOLedsageDokumentAnnulleringSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentAnnulleringSamlingHentIType.getHovedOplysninger(),
                oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
//        sbRequest.append("** Start Date: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getStartDate()).append(NEW_LINE);
//        sbRequest.append("** End Date: ").append(oioLedsageDokumentAnnulleringSamlingHentIType.getSøgeParametreStruktur().getSøgeParametre().getGyldighedPeriodeUdsøgning().getEndDate()).append(NEW_LINE);
        sbRequest.append("*******************************************************************").append(NEW_LINE);
        LOGGER.info(NEW_LINE + sbRequest.toString());


        OIOLedsageDokumentAnnulleringSamlingHentOType out = port.getOIOLedsageDokumentAnnulleringSamlingHent(oioLedsageDokumentAnnulleringSamlingHentIType);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (!hasError(out.getHovedOplysningerSvar())) {
            sb.append("** IE810 Messages: ").append(NEW_LINE);
            List<String> ie810Messages = out.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst();
            if (ie810Messages != null && ie810Messages.size() > 0) {
                for (String message : ie810Messages) {
                    sb.append(message).append(NEW_LINE);
                    sb.append("*******************************************************************").append(NEW_LINE);
                }
            } else {
                sb.append("There are no IE 810 messages!").append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            }
        }

        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }


}
