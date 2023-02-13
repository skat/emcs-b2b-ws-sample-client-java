package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE813InputStrukturType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftOpretIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentDestinationSkiftOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentDestinationSkiftOpretServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentDestinationSkiftOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentDestinationSkiftOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentDestinationSkiftOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentDestinationSkiftOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentDestinationSkiftOpret service
     */
    public OIOLedsageDokumentDestinationSkiftOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentDestinationSkiftOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie813 IE813 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie813, String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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

        // Load IE815 document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(ie813);
        Document doc = db.parse(file);

        resetTimeOfPreparation(doc, "/IE813/Header/TimeOfPreparation");
        resetDateOfPreparation(doc, "/IE813/Header/DateOfPreparation");
        resetMessageIdentifier(doc, "/IE813/Header/MessageIdentifier");
        resetDateAndTimeOfValidationOfCancellation(doc, "/IE813/Body/ChangeOfDestination/Attributes/DateAndTimeOfValidationOfChangeOfDestination");
        if (arc != null) {
            replaceValue(doc, "/IE813/Body/ChangeOfDestination/UpdateEadEsad/AdministrativeReferenceCode", arc);
        }
        // Build IE813InputStrukturType
        IE813InputStrukturType IE813InputStrukturType = new IE813InputStrukturType();
        // Set ie813 document
        IE813InputStrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentDestinationSkiftOpretIType oioLedsageDokumentDestinationSkiftOpretIType = new OIOLedsageDokumentDestinationSkiftOpretIType();
        oioLedsageDokumentDestinationSkiftOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentDestinationSkiftOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentDestinationSkiftOpretIType.setIE813InputStruktur(IE813InputStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentDestinationSkiftOpretService service = new OIOLedsageDokumentDestinationSkiftOpretService();
        OIOLedsageDokumentDestinationSkiftOpretServicePortType port = service.getOIOLedsageDokumentDestinationSkiftOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentDestinationSkiftOpretIType.getHovedOplysninger(),
                oioLedsageDokumentDestinationSkiftOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentDestinationSkiftOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info("IE813:");
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOLedsageDokumentDestinationSkiftOpretOType out = port.getOIOLedsageDokumentDestinationSkiftOpret(oioLedsageDokumentDestinationSkiftOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
    }

}
