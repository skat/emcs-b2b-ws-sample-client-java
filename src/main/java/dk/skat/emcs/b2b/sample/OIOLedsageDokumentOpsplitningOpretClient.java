package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE825StrukturType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpsplitningOpretIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpsplitningOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpsplitningOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpsplitningOpretServicePortType;
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
 * OIOLedsageDokumentOpsplitningOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentOpsplitningOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOpsplitningOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentOpsplitningOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentOpsplitningOpret service
     */
    public OIOLedsageDokumentOpsplitningOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentOpsplitningOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie825 IE825 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie825) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        File file = new File(ie825);
        Document doc = db.parse(file);

        // Build IE825StrukturType
        IE825StrukturType IE825StrukturType = new IE825StrukturType();
        // Set ie815 document
        IE825StrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentOpsplitningOpretIType oioLedsageDokumentOpsplitningOpretIType = new OIOLedsageDokumentOpsplitningOpretIType();
        oioLedsageDokumentOpsplitningOpretIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentOpsplitningOpretIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);
        oioLedsageDokumentOpsplitningOpretIType.setIE825Struktur(IE825StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentOpsplitningOpretService service = new OIOLedsageDokumentOpsplitningOpretService();
        OIOLedsageDokumentOpsplitningOpretServicePortType port = service.getOIOLedsageDokumentOpsplitningOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentOpsplitningOpretIType.getHovedOplysninger(),
                oioLedsageDokumentOpsplitningOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentOpsplitningOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOLedsageDokumentOpsplitningOpretOType out = port.getOIOLedsageDokumentOpsplitningOpret(oioLedsageDokumentOpsplitningOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
    }

}
