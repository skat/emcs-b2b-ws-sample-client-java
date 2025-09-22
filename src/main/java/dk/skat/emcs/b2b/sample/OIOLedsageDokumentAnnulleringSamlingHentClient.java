package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentAnnulleringSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;


/**
 * OIOLedsageDokumentAnnulleringSamlingHentClient
 *
 * @author SKAT
 * @since 1.0
 */
public class OIOLedsageDokumentAnnulleringSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentAnnulleringSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */

    private OIOLedsageDokumentAnnulleringSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentAnnulleringSamlingHent service
     */
    public OIOLedsageDokumentAnnulleringSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentAnnulleringSamlingHent service
     *
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException   N/A
     * @throws IOException                    N/A
     * @throws SAXException                   N/A
     */

    public OIOLedsageDokumentAnnulleringSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                                                                String afgiftOperatoerPunktAfgiftIdentifikator,
                                                                Integer interval)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(interval);


        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    public OIOLedsageDokumentAnnulleringSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                         String afgiftOperatoerPunktAfgiftIdentifikator,
                         String arcnumber)
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        SøgeParametreStrukturType søgeparameter = getSøgeParametreStrukturType(arcnumber);
        return this.invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, søgeparameter);


    }


    public OIOLedsageDokumentAnnulleringSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                            String afgiftOperatoerPunktAfgiftIdentifikator,
                            SøgeParametreStrukturType spst
    )
            throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

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
        VirksomhedIdentifikationStrukturType.Indberetter indberetter = new VirksomhedIdentifikationStrukturType.Indberetter();
        indberetter.setVirksomhedSENummerIdentifikator(virksomhedSENummerIdentifikator);
        virksomhedIdentifikationStrukturType.setIndberetter(indberetter);

        OIOLedsageDokumentAnnulleringSamlingHentIType oioLedsageDokumentAnnulleringSamlingHentIType = new OIOLedsageDokumentAnnulleringSamlingHentIType();

        oioLedsageDokumentAnnulleringSamlingHentIType.setHovedOplysninger(hovedOplysningerType);
        oioLedsageDokumentAnnulleringSamlingHentIType.setVirksomhedIdentifikationStruktur(virksomhedIdentifikationStrukturType);

        oioLedsageDokumentAnnulleringSamlingHentIType.setSøgeParametreStruktur(spst);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentAnnulleringSamlingHentService service = new OIOLedsageDokumentAnnulleringSamlingHentService();
        OIOLedsageDokumentAnnulleringSamlingHentServicePortType port = service.getOIOLedsageDokumentAnnulleringSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                oioLedsageDokumentAnnulleringSamlingHentIType.getHovedOplysninger(),
                oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                oioLedsageDokumentAnnulleringSamlingHentIType.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator(),
                spst
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());


        OIOLedsageDokumentAnnulleringSamlingHentOType out = port.getOIOLedsageDokumentAnnulleringSamlingHent(oioLedsageDokumentAnnulleringSamlingHentIType);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(out.getHovedOplysningerSvar()));
        if (out.getLedsageDokumentAnnulleringSamling() != null) {
            sb.append(generateConsoleOutput(out.getLedsageDokumentAnnulleringSamling().getIE810BeskedTekst(), "IE810"));
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return out;
    }


}
