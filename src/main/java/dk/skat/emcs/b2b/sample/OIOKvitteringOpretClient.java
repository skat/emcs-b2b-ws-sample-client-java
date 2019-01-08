package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE818InputStrukturType;
import oio.skat.emcs.ws._1_0.OIOKvitteringOpretIType;
import oio.skat.emcs.ws._1_0.OIOKvitteringOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringOpretService;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringOpretServicePortType;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * OIOKvitteringOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOKvitteringOpretClient {

    private static final Logger LOGGER = Logger.getLogger(OIOKvitteringOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOKvitteringOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOKvitteringOpret service
     */
    public OIOKvitteringOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOKvitteringOpret service
     *
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie818                                   IE818 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException   N/A
     * @throws IOException                    N/A
     * @throws SAXException                   N/A
     */

    public String invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         String ie818,boolean override) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try
        {
            builder = factory.newDocumentBuilder();
            doc = builder.parse( new InputSource( new StringReader( ie818 ) ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

            XPath xPath = XPathFactory.newInstance().newXPath();

            Node TimeOfPreparation = null;
            try {
                TimeOfPreparation = (Node) xPath.compile("/IE818/Header/TimeOfPreparation").evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            Node MessageIdentifier = null;
            try {
                MessageIdentifier = (Node) xPath.compile("/IE818/Header/MessageIdentifier").evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
            String formatDateTime = now.format(formatter);
            String result = RandomStringUtils.random(7, false, true);

            TimeOfPreparation.setTextContent(formatDateTime);
            final String uuid = UUID.randomUUID().toString();
            MessageIdentifier.setTextContent(uuid);
        if(override){
            Node LocalReferenceNumber = null;
            try {
                LocalReferenceNumber = (Node) xPath.compile("/IE818/Body/AcceptedOrRejectedReportOfReceiptExport/DestinationOffice/ReferenceNumber").evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            LocalReferenceNumber.setTextContent(result);

        }

        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);


    }



    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       File ie818) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        // Load IE818 document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(ie818);
        this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);

    }


    private String invokeit(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            Document doc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
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
        Indberetter indberetter = new Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);




        IE818InputStrukturType ie818InputStrukturType = new IE818InputStrukturType();
        ie818InputStrukturType.setAny(doc.getDocumentElement());

        OIOKvitteringOpretIType oioKvitteringOpretIType = new OIOKvitteringOpretIType();
        oioKvitteringOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioKvitteringOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioKvitteringOpretIType.setIE818InputStruktur(ie818InputStrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOKvitteringOpretService service = new OIOKvitteringOpretService();
        OIOKvitteringOpretServicePortType port = service.getOIOKvitteringOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append("*******************************************************************").append(newLine);
        sbRequest.append("** HovedOplysninger").append(newLine);
        sbRequest.append("**** Transaction Id: ").append(oioKvitteringOpretIType.getHovedOplysninger().getTransaktionIdentifikator()).append(newLine);
        sbRequest.append("**** Transaction Time: ").append(oioKvitteringOpretIType.getHovedOplysninger().getTransaktionTid()).append(newLine);
        sbRequest.append("** VirksomhedIdentifikationStruktur").append(newLine);
        sbRequest.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(oioKvitteringOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator()).append(newLine);
        sbRequest.append("**** VirksomhedSENummerIdentifikator: ").append(oioKvitteringOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()).append(newLine);
        sbRequest.append("*******************************************************************").append(newLine);
        LOGGER.info(newLine + sbRequest.toString());


        OIOKvitteringOpretOType out = port.getOIOKvitteringOpret(oioKvitteringOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(newLine);
        sb.append("** HovedOplysningerSvar").append(newLine);
        sb.append("**** Transaction Id: ").append(out.getHovedOplysningerSvar().getTransaktionIdentifikator()).append(newLine);
        sb.append("**** Transaction Time: ").append(out.getHovedOplysningerSvar().getTransaktionTid()).append(newLine);
        sb.append("**** Service Identification: ").append(out.getHovedOplysningerSvar().getServiceIdentifikator()).append(newLine);
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

        }
        sb.append("*******************************************************************").append(newLine);

        LOGGER.info(newLine + sb.toString());

        return sb.toString();
    }
}