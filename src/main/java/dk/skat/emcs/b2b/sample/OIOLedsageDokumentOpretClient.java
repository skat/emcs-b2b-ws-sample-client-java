package dk.skat.emcs.b2b.sample;

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

import java.io.*;

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

/**
 * OIOLedsageDocumentOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentOpretClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOpretClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentOpretClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDocumentOpret service
     */
    public OIOLedsageDokumentOpretClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }


    public String invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         String ie815, boolean override) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        // ie815 = doc. browser
        Document doc = loadIEDocument(ie815);
        resetDateOfPreparation(doc, "/IE815/Header/DateOfPreparation");
        resetTimeOfPreparation(doc, "/IE815/Header/TimeOfPreparation");
        resetMessageIdentifier(doc, "/IE815/Header/MessageIdentifier");
        if (override) {
            String result = RandomStringUtils.random(7, false, true);
            replaceValue(doc, "/IE815/Body/SubmittedDraftOfEADESAD/EadEsadDraft/LocalReferenceNumber", result);
        }

        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);


    }

    public String invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       File ie815) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        // Load IE815 document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(ie815);
        resetDateOfPreparation(doc, "/IE815/Header/DateOfPreparation");
        resetTimeOfPreparation(doc, "/IE815/Header/TimeOfPreparation");
        resetMessageIdentifier(doc, "/IE815/Header/MessageIdentifier");
        String result = RandomStringUtils.random(7, false, true);
        replaceValue(doc, "/IE815/Body/SubmittedDraftOfEADESAD/EadEsadDraft/LocalReferenceNumber", result);
        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }


    /**
     * Call OIOLedsageDocumentOpret service
     *
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param ie815                                   IE815 document file path.
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException   N/A
     * @throws IOException                    N/A
     * @throws SAXException                   N/A
     */
    private String invoke(String virksomhedSENummerIdentifikator,
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
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentOpretIType.getHovedOplysninger(),
                oioLedsageDokumentOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info(prettyFormatDocument(doc, 2, true));


        OIOLedsageDokumentOpretOType out = port.getOIOLedsageDokumentOpret(oioLedsageDokumentOpretIType);
        String arc = new String("");

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (!hasError(out.getHovedOplysningerSvar())) {
            if (out.getOutput().getLedsageDokument() != null) {
                sb.append("Ledsagedokument Valideret Dato: ").append(out.getOutput().getLedsageDokument().getLedsagedokumentValideretDato().toString()).append(NEW_LINE);
                sb.append("Ledsagedokument ARC Identifikator: ").append(out.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator()).append(NEW_LINE);
                arc = out.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator();
            }
            if (out.getOutput().getIE917BeskedTekst() != null) {
                sb.append("IE917 in Response:");
                sb.append(prettyFormatDocument(out.getOutput().getIE917BeskedTekst(), 2, true));
            }
        }
        sb.append("*******************************************************************").append(NEW_LINE);

        LOGGER.info(NEW_LINE + sb.toString());
        return arc;
    }


}
