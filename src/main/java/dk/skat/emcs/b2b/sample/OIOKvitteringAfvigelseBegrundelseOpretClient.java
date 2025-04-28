package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE871StrukturType;
import oio.skat.emcs.ws._1_0.OIOKvitteringAfvigelseBegrundelseOpretIType;
import oio.skat.emcs.ws._1_0.OIOKvitteringAfvigelseBegrundelseOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringAfvigelseBegrundelseOpretService;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringAfvigelseBegrundelseOpretServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * OIOKvitteringAfvigelseBegrundelseOpretClient
 *
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOKvitteringAfvigelseBegrundelseOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOKvitteringAfvigelseBegrundelseOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOKvitteringAfvigelseBegrundelseOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOKvitteringAfvigelseBegrundelseOpret service
     */
    public OIOKvitteringAfvigelseBegrundelseOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOKvitteringAfvigelseBegrundelseOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie871 IE871 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie871,
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
        Document doc = loadIEDocument(ie871);

        resetTimeOfPreparation(doc, "/IE871/Header/TimeOfPreparation");
        resetDateOfPreparation(doc, "/IE871/Header/DateOfPreparation");
        resetMessageIdentifier(doc, "/IE871/Header/MessageIdentifier");
        if (arc != null) {
            replaceValue(doc,"/IE871/Body/ExplanationOnReasonForShortage/ExciseMovement/AdministrativeReferenceCode", arc);
        }
        // Also reset date
        resetDateAndTimeOfValidationOfCancellation(doc,"/IE871/Body/ExplanationOnReasonForShortage/Attributes/DateAndTimeOfValidationOfExplanationOnShortage");

        LOGGER.info(NEW_LINE + this.prettyFormatDocument(doc, 2, true));

        // Build IE815StrukturType
        IE871StrukturType ie815StrukturType = new IE871StrukturType();
        // Set ie815 document
        ie815StrukturType.setAny(doc.getDocumentElement());

        OIOKvitteringAfvigelseBegrundelseOpretIType oioKvitteringAfvigelseBegrundelseOpretIType = new OIOKvitteringAfvigelseBegrundelseOpretIType();
        oioKvitteringAfvigelseBegrundelseOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioKvitteringAfvigelseBegrundelseOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioKvitteringAfvigelseBegrundelseOpretIType.setIE871Struktur(ie815StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOKvitteringAfvigelseBegrundelseOpretService service = new OIOKvitteringAfvigelseBegrundelseOpretService();
        OIOKvitteringAfvigelseBegrundelseOpretServicePortType port = service.getOIOKvitteringAfvigelseBegrundelseOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioKvitteringAfvigelseBegrundelseOpretIType.getHovedOplysninger(),
                oioKvitteringAfvigelseBegrundelseOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioKvitteringAfvigelseBegrundelseOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());


        OIOKvitteringAfvigelseBegrundelseOpretOType out = port.getOIOKvitteringAfvigelseBegrundelseOpret(oioKvitteringAfvigelseBegrundelseOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
    }

}
