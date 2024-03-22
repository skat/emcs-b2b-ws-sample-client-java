package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringOpretServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.springframework.binding.message.MessageContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentAnnulleringOpretClient

 * @author SKAT
 * @since 1.0
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentAnnulleringOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentAnnulleringOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentAnnulleringOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentAnnulleringOpret service
     */
    public OIOLedsageDokumentAnnulleringOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentAnnulleringOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie810 IE810 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public String  invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie810, String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        Document doc = loadIEDocument(ie810);
        resetDateOfPreparation(doc, "/IE810/Header/DateOfPreparation");
        resetTimeOfPreparation(doc, "/IE810/Header/TimeOfPreparation");
        resetMessageIdentifier(doc, "/IE810/Header/MessageIdentifier");
        resetDateAndTimeOfValidationOfCancellation(doc, "/IE810/Body/CancellationOfEAD/Attributes/DateAndTimeOfValidationOfCancellation");
        if (arc != null) {
            replaceValue(doc, "/IE810/Body/CancellationOfEAD/ExciseMovementEad/AdministrativeReferenceCode", arc);
        }
        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }

    public OIOLedsageDokumentAnnulleringOpretOType  invokeGetObject(String virksomhedSENummerIdentifikator,
                          String afgiftOperatoerPunktAfgiftIdentifikator,
                          String ie810, String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        Document doc = loadIEDocument(ie810);
        resetDateOfPreparation(doc, "/IE810/Header/DateOfPreparation");
        resetTimeOfPreparation(doc, "/IE810/Header/TimeOfPreparation");
        resetMessageIdentifier(doc, "/IE810/Header/MessageIdentifier");
        resetDateAndTimeOfValidationOfCancellation(doc, "/IE810/Body/CancellationOfEAD/Attributes/DateAndTimeOfValidationOfCancellation");
        if (arc != null) {
            replaceValue(doc, "/IE810/Body/CancellationOfEAD/ExciseMovementEad/AdministrativeReferenceCode", arc);
        }
        return this.invokeitGetObject(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }


    private String invokeit(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       Document doc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        Indberetter indberetter = new Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);


        // Build IE810InputStrukturType
        IE810InputStrukturType IE810InputStrukturType = new IE810InputStrukturType();
        // Set ie815 document
        IE810InputStrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentAnnulleringOpretIType oioLedsageDokumentAnnulleringOpretIType = new OIOLedsageDokumentAnnulleringOpretIType();
        oioLedsageDokumentAnnulleringOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentAnnulleringOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentAnnulleringOpretIType.setIE810InputStruktur(IE810InputStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentAnnulleringOpretService service = new OIOLedsageDokumentAnnulleringOpretService();
        OIOLedsageDokumentAnnulleringOpretServicePortType port = service.getOIOLedsageDokumentAnnulleringOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentAnnulleringOpretIType.getHovedOplysninger(),
                oioLedsageDokumentAnnulleringOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentAnnulleringOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOLedsageDokumentAnnulleringOpretOType out = port.getOIOLedsageDokumentAnnulleringOpret(oioLedsageDokumentAnnulleringOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
        return sb.toString();
    }


    private OIOLedsageDokumentAnnulleringOpretOType invokeitGetObject(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            Document doc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        Indberetter indberetter = new Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);


        // Build IE810InputStrukturType
        IE810InputStrukturType IE810InputStrukturType = new IE810InputStrukturType();
        // Set ie815 document
        IE810InputStrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentAnnulleringOpretIType oioLedsageDokumentAnnulleringOpretIType = new OIOLedsageDokumentAnnulleringOpretIType();
        oioLedsageDokumentAnnulleringOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentAnnulleringOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentAnnulleringOpretIType.setIE810InputStruktur(IE810InputStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentAnnulleringOpretService service = new OIOLedsageDokumentAnnulleringOpretService();
        OIOLedsageDokumentAnnulleringOpretServicePortType port = service.getOIOLedsageDokumentAnnulleringOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
            oioLedsageDokumentAnnulleringOpretIType.getHovedOplysninger(),
            oioLedsageDokumentAnnulleringOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
            oioLedsageDokumentAnnulleringOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOLedsageDokumentAnnulleringOpretOType out = port.getOIOLedsageDokumentAnnulleringOpret(oioLedsageDokumentAnnulleringOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }

    public String invoke(SamlingHentModel samlingHentModel, MessageContext context) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException, JAXBException {
        if (this.endpointURL == null){
            this.endpointURL = getEndpoint("OIOLedsageDokumentAnnulleringOpret");
        }

        OIOLedsageDokumentAnnulleringOpretOType result = invokeGetObject(samlingHentModel.getVirksomhedSENummerIdentifikator(), samlingHentModel.getAfgiftOperatoerPunktAfgiftIdentifikator(), samlingHentModel.getFile(), samlingHentModel.getARCnumber());
        addMessages(result.getHovedOplysningerSvar(), context);
        return SamlingHentMashalling.toString(result,"urn:oio:skat:emcs:ws:1.0.1","OIOLedsageDokumentAnnulleringOpret_O");
    }


}
