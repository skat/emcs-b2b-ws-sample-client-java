package dk.skat.emcs.b2b.sample;

import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE815StrukturType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpretServicePortType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang.RandomStringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.util.*;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
/**
 * OIOLedsageDocumentOpretClient
 *
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDocumentOpretClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDocumentOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDocumentOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDocumentOpret service
     */
    public OIOLedsageDocumentOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }


    public String invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie815,boolean override) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        // ie815 = doc. browser

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
        Document doc = null;
            try
            {
                builder = factory.newDocumentBuilder();
                doc = builder.parse( new InputSource( new StringReader( ie815 ) ) );
            } catch (Exception e) {
                e.printStackTrace();
            }

            XPath xPath = XPathFactory.newInstance().newXPath();

            Node TimeOfPreparation = null;
            try {
                TimeOfPreparation = (Node) xPath.compile("/IE815/Header/TimeOfPreparation").evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            Node MessageIdentifier = null;
            try {
                MessageIdentifier = (Node) xPath.compile("/IE815/Header/MessageIdentifier").evaluate(doc, XPathConstants.NODE);
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
                LocalReferenceNumber = (Node) xPath.compile("/IE815/Body/SubmittedDraftOfEAD/EadDraft/LocalReferenceNumber").evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            LocalReferenceNumber.setTextContent(result);

        }

        return this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);


    }

    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       File ie815) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        // Load IE815 document



        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(ie815);


        XPath xPath = XPathFactory.newInstance().newXPath();

        Node TimeOfPreparation = null;
        try {
            TimeOfPreparation = (Node) xPath.compile("/IE815/Header/TimeOfPreparation").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        Node MessageIdentifier = null;
        try {
            MessageIdentifier = (Node) xPath.compile("/IE815/Header/MessageIdentifier").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        Node LocalReferenceNumber = null;
        try {
            LocalReferenceNumber = (Node) xPath.compile("/IE815/Body/SubmittedDraftOfEAD/EadDraft/LocalReferenceNumber").evaluate(doc, XPathConstants.NODE);
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
        LocalReferenceNumber.setTextContent(result);
        final String newLine = System.getProperty("line.separator");
        LOGGER.info(newLine + "Replacing message Identifier: " + TimeOfPreparation.getTextContent() +" "+ MessageIdentifier.getTextContent() + " " + LocalReferenceNumber.getTextContent()) ;

        this.invokeit(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }


    /**
     * Call OIOLedsageDocumentOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie815 IE815 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
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

        // Build IE815StrukturType
        IE815StrukturType ie815StrukturType = new IE815StrukturType();
        // Set ie815 document
        ie815StrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentOpretIType oioLedsageDokumentOpretIType = new OIOLedsageDokumentOpretIType();
        oioLedsageDokumentOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentOpretIType.setIE815Struktur(ie815StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentOpretService service = new OIOLedsageDokumentOpretService();
        OIOLedsageDokumentOpretServicePortType port = service.getOIOLedsageDokumentOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append("*******************************************************************").append(newLine);
        sbRequest.append("** HovedOplysninger").append(newLine);
        sbRequest.append("**** Transaction Id: ").append(oioLedsageDokumentOpretIType.getHovedOplysninger().getTransaktionIdentifikator()).append(newLine);
        sbRequest.append("**** Transaction Time: ").append(oioLedsageDokumentOpretIType.getHovedOplysninger().getTransaktionTid()).append(newLine);
        sbRequest.append("** VirksomhedIdentifikationStruktur").append(newLine);
        sbRequest.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(oioLedsageDokumentOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator()).append(newLine);
        sbRequest.append("**** VirksomhedSENummerIdentifikator: ").append(oioLedsageDokumentOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()).append(newLine);
        sbRequest.append("*******************************************************************").append(newLine);
        LOGGER.info(newLine + sbRequest.toString());


        OIOLedsageDokumentOpretOType out = port.getOIOLedsageDokumentOpret(oioLedsageDokumentOpretIType);
        String arc = new String("");

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

                sb.append("Ledsagedokument Valideret Dato: ").append(out.getOutput().getLedsageDokument().getLedsagedokumentValideretDato().toString()).append(newLine);
                sb.append("Ledsagedokument ARC Identifikator: ").append(out.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator()).append(newLine);
                arc = out.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();

        }
        sb.append("*******************************************************************").append(newLine);

        LOGGER.info(newLine + sb.toString());


            return arc;

        }



}
