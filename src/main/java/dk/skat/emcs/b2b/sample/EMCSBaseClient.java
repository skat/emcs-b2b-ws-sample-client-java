package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerSvarType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;
import oio.skat.emcs.ws._1_0.VirksomhedIdentifikationStrukturType;
import org.apache.cxf.endpoint.Client;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * EMCSBaseClient
 *
 * @author SKAT
 * @since 1.2
 */
public class EMCSBaseClient {

    protected static final String NEW_LINE = System.getProperty("line.separator");

    protected void addCleartextLogging(Client client) {
        CleartextLogger clearOutputLogger = new CleartextLogger();
        CleartextLogger clearInputLogger = new CleartextLogger();
        client.getOutInterceptors().add(clearOutputLogger);
        client.getInInterceptors().add(clearInputLogger);
    }

    protected Document loadIEDocument(String path) throws IOException, SAXException, ParserConfigurationException {
        File file = new File(path);
        return loadIEDocument(file);
    }

    protected Document loadIEDocument(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        return doc;
    }

    protected HovedOplysningerType generateHovedOplysningerType() throws DatatypeConfigurationException {
        // Generate Transaction Id
        final String transactionID = TransactionIdGenerator.getTransactionId();
        return generateHovedOplysningerType(transactionID);
    }

    protected HovedOplysningerType generateHovedOplysningerType(String transactionID) throws DatatypeConfigurationException {
        // Generate Transaction Time
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        XMLGregorianCalendar transactionTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        // Build HovedOplysninger Object and set transaction id and time
        HovedOplysningerType hovedOplysningerType = new HovedOplysningerType();
        hovedOplysningerType.setTransaktionIdentifikator(transactionID);
        hovedOplysningerType.setTransaktionTid(transactionTime);
        return hovedOplysningerType;
    }

    protected VirksomhedIdentifikationStrukturType generateVirksomhedIdentifikationStrukturType(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator) {
        // Build VirksomhedIdentifikationStruktur
        VirksomhedIdentifikationStrukturType virksomhedIdentifikationStrukturType = new VirksomhedIdentifikationStrukturType();
        virksomhedIdentifikationStrukturType.setAfgiftOperatoerPunktAfgiftIdentifikator(afgiftOperatoerPunktAfgiftIdentifikator);
        VirksomhedIdentifikationStrukturType.Indberetter indberetter = new VirksomhedIdentifikationStrukturType.Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);
        return virksomhedIdentifikationStrukturType;

    }


    protected SøgeParametreStrukturType getSøgeParametreStrukturType(Integer interval) throws DatatypeConfigurationException {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning gyldighedPeriodeUdsøgning
                = new SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning();
        soegeParametre.setGyldighedPeriodeUdsøgning(gyldighedPeriodeUdsøgning);

        // Search for messages in the period: now minus 1 month -- to -- now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -interval);
        Date startDate = cal.getTime();
        gyldighedPeriodeUdsøgning.setStartDate(getXMLGregorianCalendar(startDate));
        gyldighedPeriodeUdsøgning.setEndDate(getXMLGregorianCalendar(new Date()));
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    protected SøgeParametreStrukturType getSøgeParametreStrukturType(String arcnumber) throws DatatypeConfigurationException {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        soegeParametre.setLedsagedokumentARCIdentifikator(arcnumber);
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    /**
     * Generate output of HovedOplysningerType document for console
     *
     * @param hovedOplysningerType HovedOplysningerType document (found in service response)
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerType hovedOplysningerType) {
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(NEW_LINE);
        sb.append("** HovedOplysninger").append(NEW_LINE);
        sb.append("**** Transaction Id: ").append(hovedOplysningerType.getTransaktionIdentifikator()).append(NEW_LINE);
        sb.append("**** Transaction Time: ").append(hovedOplysningerType.getTransaktionTid()).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        return sb.toString();
    }

    /**
     * Generate output of HovedOplysningerType document for console together with fields:
     * <p>
     * AfgiftOperatoerPunktAfgiftIdentifikator
     * VirksomhedSENummerIdentifikator
     *
     * @param hovedOplysningerType                    HovedOplysningerType document (found in service response)
     * @param afgiftOperatoerPunktAfgiftIdentifikator
     * @param virksomhedSENummerIdentifikator
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerType hovedOplysningerType,
                                           String afgiftOperatoerPunktAfgiftIdentifikator,
                                           String virksomhedSENummerIdentifikator,
                                           SøgeParametreStrukturType spst) {
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(hovedOplysningerType));
        sb.append("** VirksomhedIdentifikationStruktur").append(NEW_LINE);
        sb.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(afgiftOperatoerPunktAfgiftIdentifikator).append(NEW_LINE);
        sb.append("**** VirksomhedSENummerIdentifikator: ").append(virksomhedSENummerIdentifikator).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        if (spst != null) {
            boolean isSearchByArc = spst.getSøgeParametre().getLedsagedokumentARCIdentifikator() != null;
            if (!isSearchByArc){
                sb.append("** Start Date: ").append(spst.getSøgeParametre().getGyldighedPeriodeUdsøgning().getStartDate()).append(NEW_LINE);
                sb.append("** End Date: ").append(spst.getSøgeParametre().getGyldighedPeriodeUdsøgning().getEndDate()).append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            } else {
                sb.append("** ARC: ").append(spst.getSøgeParametre().getLedsagedokumentARCIdentifikator()).append(NEW_LINE);
                sb.append("*******************************************************************").append(NEW_LINE);
            }

        }
        return sb.toString();
    }

    protected String generateConsoleOutput(HovedOplysningerType hovedOplysningerType,
                                           String afgiftOperatoerPunktAfgiftIdentifikator,
                                           String virksomhedSENummerIdentifikator) {
        return generateConsoleOutput(hovedOplysningerType, afgiftOperatoerPunktAfgiftIdentifikator, virksomhedSENummerIdentifikator, null);
    }

    /**
     * Generate out of HovedOplysningerSvarType document for console
     *
     * @param hovedOplysningerSvarType HovedOplysningerSvarType document (found in service response)
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerSvarType hovedOplysningerSvarType) {
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(NEW_LINE);
        sb.append("** HovedOplysningerSvar").append(NEW_LINE);
        sb.append("**** Transaction Id: ").append(hovedOplysningerSvarType.getTransaktionIdentifikator()).append(NEW_LINE);
        sb.append("**** Transaction Time: ").append(hovedOplysningerSvarType.getTransaktionTid()).append(NEW_LINE);
        sb.append("**** Service Identification: ").append(hovedOplysningerSvarType.getServiceIdentifikator()).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        if (hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    FejlStrukturType fejlStrukturType = (FejlStrukturType) errorOrAdvis;
                    sb.append("**** Error").append(NEW_LINE);
                    sb.append("****** Error Code: ").append(fejlStrukturType.getFejlIdentifikator()).append(NEW_LINE);
                    sb.append("****** Error Text: ").append(fejlStrukturType.getFejlTekst()).append(NEW_LINE);
                }
                if (errorOrAdvis instanceof AdvisStrukturType) {
                    AdvisStrukturType advisStrukturType = (AdvisStrukturType) errorOrAdvis;
                    sb.append("**** Advis").append(NEW_LINE);
                    sb.append("****** Advis Code: ").append(advisStrukturType.getAdvisIdentifikator()).append(NEW_LINE);
                    sb.append("****** Advis Text: ").append(advisStrukturType.getAdvisTekst()).append(NEW_LINE);
                }
            }
            sb.append("*******************************************************************").append(NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * Test if header object contains at least one error.
     *
     * @param hovedOplysningerSvarType HovedOplysningerSvarType document (found in service response)
     * @return True if at least one error.
     */
    protected boolean hasError(HovedOplysningerSvarType hovedOplysningerSvarType) {
        boolean hasError = false;
        for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
            if (errorOrAdvis instanceof FejlStrukturType) {
                hasError = true;
            }
        }
        return hasError;
    }

    protected void resetTimeOfPreparation(Document doc, String path) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatDateTime = now.format(formatter);
        replaceValue(doc, path, formatDateTime);
    }

    protected void resetDateOfPreparation(Document doc, String path) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = now.format(formatter);
        replaceValue(doc, path, formatDateTime);
    }

    protected void resetDateAndTimeOfValidationOfCancellation(Document doc, String path) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formatDateTime = now.format(formatter);
        replaceValue(doc, path, formatDateTime);
    }

    protected void resetMessageIdentifier(Document doc, String path) {
        final String uuid = UUID.randomUUID().toString();
        replaceValue(doc, path, uuid);
    }

    protected void resetMessageIdentifier(Document doc, String path, String messageIdentifier) {
        replaceValue(doc, path, messageIdentifier);
    }

    protected void replaceValue(Document doc, String path, String value) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node node = null;
        try {
            node = (Node) xPath.compile(path).evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        node.setTextContent(value);
    }

    public static String prettyFormatDocument(Document document, int indent, boolean ignoreDeclaration) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ignoreDeclaration ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Writer out = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(out));
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:", e);
        }
    }

    public static String prettyFormatDocument(String xmlString, int indent, boolean ignoreDeclaration) {

        try {
            InputSource src = new InputSource(new StringReader(xmlString));
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);
            return prettyFormatDocument(document, indent, ignoreDeclaration);
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:" + xmlString, e);
        }
    }


}
