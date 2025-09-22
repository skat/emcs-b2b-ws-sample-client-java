package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE837StrukturType;
import oio.skat.emcs.ws._1_0.OIOForsinkelseForklaringOpretIType;
import oio.skat.emcs.ws._1_0.OIOForsinkelseForklaringOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOForsinkelseForklaringOpretService;
import oio.skat.emcs.ws._1_0_1.OIOForsinkelseForklaringOpretServicePortType;
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
import jakarta.xml.ws.BindingProvider;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * OIOForsinkelseForklaringOpretClient

 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOForsinkelseForklaringOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOForsinkelseForklaringOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOForsinkelseForklaringOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOForsinkelseForklaringOpret service
     */
    public OIOForsinkelseForklaringOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOForsinkelseForklaringOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie837 IE837 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public OIOForsinkelseForklaringOpretOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie837, String arc, String submitterIdentification) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        File file = new File(ie837);
        Document doc = db.parse(file);

        resetTimeOfPreparation(doc, "/IE837/Header/TimeOfPreparation");
        resetDateOfPreparation(doc, "/IE837/Header/DateOfPreparation");
        resetMessageIdentifier(doc, "/IE837/Header/MessageIdentifier");
        resetDateAndTimeOfValidationOfCancellation(doc,"/IE837/Body/ExplanationOnDelayForDelivery/Attributes/DateAndTimeOfValidationOfExplanationOnDelay");
        if (arc != null) {
            replaceValue(doc, "/IE837/Body/ExplanationOnDelayForDelivery/ExciseMovement/AdministrativeReferenceCode", arc);
        }
        replaceValue(doc, "/IE837/Body/ExplanationOnDelayForDelivery/Attributes/SubmitterIdentification",submitterIdentification);
        // Build IE815StrukturType
        IE837StrukturType ie837StrukturType = new IE837StrukturType();
        // Set ie815 document
        ie837StrukturType.setAny(doc.getDocumentElement());

        OIOForsinkelseForklaringOpretIType oioForsinkelseForklaringOpretIType = new OIOForsinkelseForklaringOpretIType();
        oioForsinkelseForklaringOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioForsinkelseForklaringOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioForsinkelseForklaringOpretIType.setIE837Struktur(ie837StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOForsinkelseForklaringOpretService service = new OIOForsinkelseForklaringOpretService();
        OIOForsinkelseForklaringOpretServicePortType port = service.getOIOForsinkelseForklaringOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioForsinkelseForklaringOpretIType.getHovedOplysninger(),
                oioForsinkelseForklaringOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioForsinkelseForklaringOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info("IE837:");
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOForsinkelseForklaringOpretOType out = port.getOIOForsinkelseForklaringOpret(oioForsinkelseForklaringOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        String ie917 = out.getIE917BeskedTekst();
        if (ie917 != null) {
            LOGGER.info("IE917:");
            LOGGER.info(prettyFormatDocument(ie917, 2, true));

        }

        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }

}
