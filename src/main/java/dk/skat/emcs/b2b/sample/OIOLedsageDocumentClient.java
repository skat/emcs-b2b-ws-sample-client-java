package dk.skat.emcs.b2b.sample;

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

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * OIOLedsageDocumentClient
 *
 * @author SKAT
 * @since 1.0
 */
public class OIOLedsageDocumentClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDocumentClient.class.getName());

    public void invoke() throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        String ie815 = "ie815.xml";
        String virksomhedSENummerIdentifikator = "30808460";
        String afgiftOperatoerPunktAfgiftIdentifikator = "DK82065873300";

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

        File file = new File(ie815);
        Document doc = db.parse(file);

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

        OIOLedsageDokumentOpretOType out = port.getOIOLedsageDokumentOpret(oioLedsageDokumentOpretIType);
        StringBuilder sb = new StringBuilder();
        final String newLine = System.getProperty("line.separator");
        sb.append("** HovedOplysningerSvar").append(newLine);
        sb.append("**** Transaction Id: " + out.getHovedOplysningerSvar().getTransaktionIdentifikator()).append(newLine);
        sb.append("**** Transaction Time: " + out.getHovedOplysningerSvar().getTransaktionTid()).append(newLine);
        sb.append("**** Service Identification: " + out.getHovedOplysningerSvar().getServiceIdentifikator()).append(newLine);
        if (out.getHovedOplysningerSvar().getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : out.getHovedOplysningerSvar().getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    FejlStrukturType fejlStrukturType = (FejlStrukturType) errorOrAdvis;
                    sb.append("**** Error").append(newLine);
                    sb.append("****** Error Code: " + fejlStrukturType.getFejlIdentifikator()).append(newLine);
                    sb.append("****** Error Text: " + fejlStrukturType.getFejlTekst()).append(newLine);
                }
                if (errorOrAdvis instanceof AdvisStrukturType) {
                    AdvisStrukturType advisStrukturType = (AdvisStrukturType) errorOrAdvis;
                    sb.append("**** Advis");
                    sb.append("****** Advis Code: " + advisStrukturType.getAdvisIdentifikator()).append(newLine);
                    sb.append("****** Advis Text: " + advisStrukturType.getAdvisTekst()).append(newLine);
                }
            }
        } else {
            sb.append("Ledsagedokument Valideret Dato: " + out.getOutput().getLedsageDokument().getLedsagedokumentValideretDato().toString()).append(newLine);
            sb.append("Ledsagedokument ARC Identifikator: " + out.getOutput().getLedsageDokument().getLedsagedokumentARCIdentifikator()).append(newLine);
        }

        LOGGER.info(newLine + sb.toString());
    }

}
