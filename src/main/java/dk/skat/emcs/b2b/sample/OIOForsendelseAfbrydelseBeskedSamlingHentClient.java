package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import oio.skat.emcs.ws._1_0.OIOEksportGodkendelseSamlingHentOType;
import oio.skat.emcs.ws._1_0.OIOForsendelseAfbrydelseBeskedSamlingHentIType;
import oio.skat.emcs.ws._1_0.OIOForsendelseAfbrydelseBeskedSamlingHentOType;
import oio.skat.emcs.ws._1_0_1.OIOForsendelseAfbrydelseBeskedSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOForsendelseAfbrydelseBeskedSamlingHentServicePortType;
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

public class OIOForsendelseAfbrydelseBeskedSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOForsendelseAfbrydelseBeskedSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOForsendelseAfbrydelseBeskedSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOForsendelseAfbrydelseBeskedSamlingHent service
     */
    public OIOForsendelseAfbrydelseBeskedSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOForsendelseAfbrydelseBeskedSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator) throws DatatypeConfigurationException {

        OIOForsendelseAfbrydelseBeskedSamlingHentIType request = new OIOForsendelseAfbrydelseBeskedSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOForsendelseAfbrydelseBeskedSamlingHentService service = new OIOForsendelseAfbrydelseBeskedSamlingHentService();
        OIOForsendelseAfbrydelseBeskedSamlingHentServicePortType port = service.getOIOForsendelseAfbrydelseBeskedSamlingHentServicePort();

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

        OIOForsendelseAfbrydelseBeskedSamlingHentOType response = port.getOIOForsendelseAfbrydelseBeskedSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));

        if (response.getForsendelseAfbrydelseBeskedSamling() != null){
            List<String> list = response.getForsendelseAfbrydelseBeskedSamling().getIE807BeskedTekst();
            int i = 1;
            for (String message : list) {
                sb.append(NEW_LINE + "Message " + i + ":");
                sb.append(NEW_LINE + message);
                i++;
            }
        }

        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }

    public String invoke(SamlingHentModel samlingHentModel, MessageContext context) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException, JAXBException {
        if (this.endpointURL == null){
            this.endpointURL = getEndpoint("OIOForsendelseAfbrydelseBeskedSamlingHent");
        }
        OIOForsendelseAfbrydelseBeskedSamlingHentOType result = invoke(samlingHentModel.getVirksomhedSENummerIdentifikator(), samlingHentModel.getAfgiftOperatoerPunktAfgiftIdentifikator());
        addMessages(result.getHovedOplysningerSvar(), context);
        return SamlingHentMashalling.toString(result,"urn:oio:skat:emcs:ws:1.0.1","OIOForsendelseAfbrydelseBeskedSamlingHent_O");
    }


}
