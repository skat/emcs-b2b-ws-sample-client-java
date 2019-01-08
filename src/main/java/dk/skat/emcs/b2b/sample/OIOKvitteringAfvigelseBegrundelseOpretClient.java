package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
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
 * OIOKvitteringAfvigelseBegrundelseOpretClient
 *
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOKvitteringAfvigelseBegrundelseOpretClient {

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
                       String ie871) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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

        // Load IE815 document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(ie871);
        Document doc = db.parse(file);

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

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append("*******************************************************************").append(newLine);
        sbRequest.append("** HovedOplysninger").append(newLine);
        sbRequest.append("**** Transaction Id: ").append(oioKvitteringAfvigelseBegrundelseOpretIType.getHovedOplysninger().getTransaktionIdentifikator()).append(newLine);
        sbRequest.append("**** Transaction Time: ").append(oioKvitteringAfvigelseBegrundelseOpretIType.getHovedOplysninger().getTransaktionTid()).append(newLine);
        sbRequest.append("** VirksomhedIdentifikationStruktur").append(newLine);
        sbRequest.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(oioKvitteringAfvigelseBegrundelseOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator()).append(newLine);
        sbRequest.append("**** VirksomhedSENummerIdentifikator: ").append(oioKvitteringAfvigelseBegrundelseOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()).append(newLine);
        sbRequest.append("*******************************************************************").append(newLine);
        LOGGER.info(newLine + sbRequest.toString());


        OIOKvitteringAfvigelseBegrundelseOpretOType out = port.getOIOKvitteringAfvigelseBegrundelseOpret(oioKvitteringAfvigelseBegrundelseOpretIType);
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
    }

}
