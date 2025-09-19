package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.IE818InputStrukturType;
import oio.skat.emcs.ws._1_0.OIOKvitteringOpretIType;
import oio.skat.emcs.ws._1_0.OIOKvitteringOpretOType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType.Indberetter;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringOpretService;
import oio.skat.emcs.ws._1_0_1.OIOKvitteringOpretServicePortType;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * OIOKvitteringOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOKvitteringOpretClient extends EMCSBaseClient {

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

    public OIOKvitteringOpretOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         String ie818,boolean override) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {


        Document doc = loadIEDocument(ie818);
        String result = RandomStringUtils.random(7, false, true);
        if(override){
            replaceValue(doc, "/IE818/Body/AcceptedOrRejectedReportOfReceiptExport/DestinationOffice/ReferenceNumber", result);
        }
        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);


    }



    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       File ie818) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        Document doc = loadIEDocument(ie818);
        this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }

    public OIOKvitteringOpretOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       File ie818,
                       String arc) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        Document doc = loadIEDocument(ie818);
        this.replaceValue(doc, "/IE818/Body/AcceptedOrRejectedReportOfReceiptExport/ExciseMovement/AdministrativeReferenceCode", arc);
        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, doc);
    }


    private OIOKvitteringOpretOType invoke(String virksomhedSENummerIdentifikator,
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

        resetTimeOfPreparation(doc, "/IE818/Header/TimeOfPreparation");
        resetDateOfPreparation(doc, "/IE818/Header/DateOfPreparation");
        resetMessageIdentifier(doc, "/IE818/Header/MessageIdentifier");
        replaceValue(doc,"/IE818/Body/AcceptedOrRejectedReportOfReceiptExport/ConsigneeTrader/Traderid", afgiftOperatoerPunktAfgiftIdentifikator);
        // Also reset date
        resetDateAndTimeOfValidationOfCancellation(doc,"/IE818/Body/AcceptedOrRejectedReportOfReceiptExport/Attributes/DateAndTimeOfValidationOfReportOfReceiptExport");

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
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioKvitteringOpretIType.getHovedOplysninger(),
                oioKvitteringOpretIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioKvitteringOpretIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());
        LOGGER.info(prettyFormatDocument(doc, 2, true));

        OIOKvitteringOpretOType out = port.getOIOKvitteringOpret(oioKvitteringOpretIType);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        LOGGER.info(NEW_LINE + sb.toString());

        return out;
    }
}