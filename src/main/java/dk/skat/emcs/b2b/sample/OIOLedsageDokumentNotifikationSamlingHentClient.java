package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationSamlingHentIType;
import oio.skat.emcs.ws._1_0.OIOLedsageDokumentNotifikationSamlingHentOType;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentNotifikationSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOLedsageDokumentNotifikationSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;
import java.util.List;
import java.util.logging.Logger;

public class OIOLedsageDokumentNotifikationSamlingHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOLedsageDokumentNotifikationSamlingHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOLedsageDokumentNotifikationSamlingHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOLedsageDokumentNotifikationSamlingHent service
     */
    public OIOLedsageDokumentNotifikationSamlingHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOLedsageDokumentNotifikationSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator) throws DatatypeConfigurationException {

        OIOLedsageDokumentNotifikationSamlingHentIType request = new OIOLedsageDokumentNotifikationSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOLedsageDokumentNotifikationSamlingHentService service = new OIOLedsageDokumentNotifikationSamlingHentService();
        OIOLedsageDokumentNotifikationSamlingHentServicePortType port = service.getOIOLedsageDokumentNotifikationSamlingHentServicePort();

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

        OIOLedsageDokumentNotifikationSamlingHentOType response = port.getOIOLedsageDokumentNotifikationSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));
        List<String> list = response.getLedsageDokumentNotifikationSamling().getIE819BeskedTekst();
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
