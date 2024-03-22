package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOEksportAfvisningSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOEksportAfvisningSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.springframework.binding.message.MessageContext;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class OIOEksportAfvisningSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOEksportAfvisningSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOEksportAfvisningSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOEksportAfvisningSamlingHent service
     */
    public OIOEksportAfvisningSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOEksportAfvisningSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator) throws DatatypeConfigurationException {

        OIOEksportAfvisningSamlingHentIType request = new OIOEksportAfvisningSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOEksportAfvisningSamlingHentService service = new OIOEksportAfvisningSamlingHentService();
        OIOEksportAfvisningSamlingHentServicePortType port = service.getOIOEksportAfvisningSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                request.getHovedOplysninger(),
                request.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                request.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOEksportAfvisningSamlingHentOType response = port.getOIOEksportAfvisningSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));

        if(response.getEksportAfvisningSamling() != null){
            List<String> list = response.getEksportAfvisningSamling().getIE839BeskedTekst();
            int i = 1;
            for (String message : list) {
                sb.append(NEW_LINE + "Message " + i + ":");
                sb.append(NEW_LINE + prettyFormatDocument(message, 2, true));
                i++;
            }
        }

        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }

    public String invoke(SamlingHentModel samlingHentModel, MessageContext context) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException, JAXBException {
        if (this.endpointURL == null){
            this.endpointURL = getEndpoint("OIOEksportAfvisningSamlingHent");
        }
        OIOEksportAfvisningSamlingHentOType result = invoke(samlingHentModel.getVirksomhedSENummerIdentifikator(), samlingHentModel.getAfgiftOperatoerPunktAfgiftIdentifikator());
        addMessages(result.getHovedOplysningerSvar(), context);
        return SamlingHentMashalling.toString(result,"urn:oio:skat:emcs:ws:1.0.1","OIOEksportAfvisningSamlingHent_O");
    }
}
