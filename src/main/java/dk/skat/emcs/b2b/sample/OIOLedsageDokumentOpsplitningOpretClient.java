package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.IE825StrukturType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpsplitningOpretIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentOpsplitningOpretOType;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpsplitningOpretService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOpsplitningOpretServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jakarta.xml.ws.BindingProvider;
import java.io.File;
import java.io.IOException;
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
    public OIOLedsageDokumentOpsplitningOpretOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String ie825, String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        // Load IE815 document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(ie825);
        Document doc = db.parse(file);

        resetTimeOfPreparation(doc, "/IE825/Header/TimeOfPreparation");
        resetDateOfPreparation(doc, "/IE825/Header/DateOfPreparation");
        resetMessageIdentifier(doc, "/IE825/Header/MessageIdentifier");
        replaceValue(doc,"/IE825/Body/SubmittedDraftOfSplittingOperation/SplittingEad/UpstreamArc", arc);


        // Build IE825StrukturType
        IE825StrukturType IE825StrukturType = new IE825StrukturType();
        // Set ie815 document
        IE825StrukturType.setAny(doc.getDocumentElement());

        OIOLedsageDokumentOpsplitningOpretIType oioLedsageDokumentOpsplitningOpretIType = new OIOLedsageDokumentOpsplitningOpretIType();
        oioLedsageDokumentOpsplitningOpretIType.setHovedOplysninger(generateHovedOplysningerType());
        oioLedsageDokumentOpsplitningOpretIType.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        oioLedsageDokumentOpsplitningOpretIType.setIE825Struktur(IE825StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentOpsplitningOpretService service = new OIOLedsageDokumentOpsplitningOpretService();
        OIOLedsageDokumentOpsplitningOpretServicePortType port = service.getOIOLedsageDokumentOpsplitningOpretServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentOpsplitningOpretIType.getHovedOplysninger(),
                oioLedsageDokumentOpsplitningOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentOpsplitningOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info("IE825:");
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOLedsageDokumentOpsplitningOpretOType out = port.getOIOLedsageDokumentOpsplitningOpret(oioLedsageDokumentOpsplitningOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }

}
