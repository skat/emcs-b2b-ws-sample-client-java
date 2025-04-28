package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOmdirigeretAdvisSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentOmdirigeretAdvisSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * OIOLedsageDokumentOpsplitningOpretClient
 *
 * @author SKAT
 * @since 1.2
 */
@SuppressWarnings("ALL")
public class OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentOmdirigeretAdvisSamlingHent service
     */
    public OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Call OIOLedsageDokumentOpsplitningOpret service
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @throws DatatypeConfigurationException N/A
     * @throws ParserConfigurationException N/A
     * @throws IOException N/A
     * @throws SAXException N/A
     */
    public void invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {

        OIOLedsageDokumentOmdirigeretAdvisSamlingHentIType request = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);

        OIOLedsageDokumentOmdirigeretAdvisSamlingHentService service = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentService();
        OIOLedsageDokumentOmdirigeretAdvisSamlingHentServicePortType port = service.getOIOLedsageDokumentOmdirigeretAdvisSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                request.getHovedOplysninger(),
                request.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                request.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOLedsageDokumentOmdirigeretAdvisSamlingHentOType response = port.getOIOLedsageDokumentOmdirigeretAdvisSamlingHent(request);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));
        List<String> list = response.getLedsageDokumentOmdirigeretAdvisSamling().getIE803BeskedTekst();
        int i = 1;
        for (String message : list) {
            sb.append(NEW_LINE + "Message " + i + ":");
            sb.append(NEW_LINE);
            sb.append(prettyFormatDocument(message, 2, true)).append(NEW_LINE);

            i++;
        }
        LOGGER.info(NEW_LINE + sb.toString());
    }

}
