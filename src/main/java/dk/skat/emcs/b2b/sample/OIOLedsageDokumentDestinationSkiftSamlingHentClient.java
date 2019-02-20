package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftSamlingHentIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentDestinationSkiftSamlingHentOType;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentDestinationSkiftSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentDestinationSkiftSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;
import java.util.List;
import java.util.logging.Logger;

public class OIOLedsageDokumentDestinationSkiftSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentDestinationSkiftSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentDestinationSkiftSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentDestinationSkiftSamlingHent service
     */
    public OIOLedsageDokumentDestinationSkiftSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOLedsageDokumentDestinationSkiftSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator) throws DatatypeConfigurationException {

        OIOLedsageDokumentDestinationSkiftSamlingHentIType request = new OIOLedsageDokumentDestinationSkiftSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOLedsageDokumentDestinationSkiftSamlingHentService service = new OIOLedsageDokumentDestinationSkiftSamlingHentService();
        OIOLedsageDokumentDestinationSkiftSamlingHentServicePortType port = service.getOIOLedsageDokumentDestinationSkiftSamlingHentServicePort();

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

        OIOLedsageDokumentDestinationSkiftSamlingHentOType response = port.getOIOLedsageDokumentDestinationSkiftSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));
        List<String> list = response.getLedsageDokumentDestinationSkiftSamling().getIE813BeskedTekst();
        int i = 1;
        for (String message : list) {
            sb.append(NEW_LINE + "Message " + i + ":");
            sb.append(NEW_LINE + message);
            i++;
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }


}
