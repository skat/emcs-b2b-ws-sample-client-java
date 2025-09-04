package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE819InputStrukturType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationOpretIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentNotifikationOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentNotifikationOpretServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
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
 * OIOLedsageDokumentNotifikationOpretClient
 *
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentNotifikationOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentNotifikationOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentNotifikationOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentNotifikationOpret service
     */
    public OIOLedsageDokumentNotifikationOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentNotifikationOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie819 IE819 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie819) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, ie819, null);
    }


        /**
         * Call OIOLedsageDokumentNotifikationOpret service
         *
         * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
         * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
         * @param ie819 IE819 document file path.
         * @param arc Reference no.
         * @throws DatatypeConfigurationException N/A
         * @throws ParserConfigurationException N/A
         * @throws IOException N/A
         * @throws SAXException N/A
         */
    public OIOLedsageDokumentNotifikationOpretOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie819,
                       String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        File file = new File(ie819);
        Document doc = db.parse(file);

        resetDateOfPreparation(doc, "/IE819/Header/DateOfPreparation");
        resetTimeOfPreparation(doc, "/IE819/Header/TimeOfPreparation");
        resetMessageIdentifier(doc, "/IE819/Header/MessageIdentifier");
        if (arc != null) {
            replaceValue(doc, "/IE819/Body/AlertOrRejectionOfEADESAD/ExciseMovement/AdministrativeReferenceCode", arc);
        }
        resetDateAndTimeOfValidationOfCancellation(doc,"/IE819/Body/AlertOrRejectionOfEADESAD/Attributes/DateAndTimeOfValidationOfAlertRejection" );
        resetDateOfPreparation(doc,"/IE819/Body/AlertOrRejectionOfEADESAD/AlertOrRejection/DateOfAlertOrRejection" );

        // Build IE819InputStrukturType
        IE819InputStrukturType IE819InputStrukturType = new IE819InputStrukturType();
        // Set ie815 document
        IE819InputStrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentNotifikationOpretIType oioLedsageDokumentNotifikationOpretIType = new OIOLedsageDokumentNotifikationOpretIType();
        oioLedsageDokumentNotifikationOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentNotifikationOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentNotifikationOpretIType.setIE819InputStruktur(IE819InputStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentNotifikationOpretService service = new OIOLedsageDokumentNotifikationOpretService();
        OIOLedsageDokumentNotifikationOpretServicePortType port = service.getOIOLedsageDokumentNotifikationOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentNotifikationOpretIType.getHovedOplysninger(),
                oioLedsageDokumentNotifikationOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentNotifikationOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOLedsageDokumentNotifikationOpretOType out = port.getOIOLedsageDokumentNotifikationOpret(oioLedsageDokumentNotifikationOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }

}
