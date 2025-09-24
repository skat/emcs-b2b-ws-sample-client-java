package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOForsendelseAfbrydelseBeskedSamlingHentIType;
import oio.skat.emcs.ws._1_0.OIOForsendelseAfbrydelseBeskedSamlingHentOType;
import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;
import oio.skat.emcs.ws._1_0_1.OIOForsendelseAfbrydelseBeskedSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOForsendelseAfbrydelseBeskedSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;

import javax.xml.datatype.DatatypeConfigurationException;
import jakarta.xml.ws.BindingProvider;
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
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       SøgeParametreStrukturType spst) throws DatatypeConfigurationException {

        OIOForsendelseAfbrydelseBeskedSamlingHentIType request = new OIOForsendelseAfbrydelseBeskedSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(spst);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOForsendelseAfbrydelseBeskedSamlingHentService service = new OIOForsendelseAfbrydelseBeskedSamlingHentService();
        OIOForsendelseAfbrydelseBeskedSamlingHentServicePortType port = service.getOIOForsendelseAfbrydelseBeskedSamlingHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                request.getHovedOplysninger(),
                request.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                request.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator(),
                spst
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOForsendelseAfbrydelseBeskedSamlingHentOType response = port.getOIOForsendelseAfbrydelseBeskedSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));
        if (response.getForsendelseAfbrydelseBeskedSamling() != null) {
            sb.append(generateConsoleOutput(response.getForsendelseAfbrydelseBeskedSamling().getIE807BeskedTekst(), "IE807"));
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }


}
