package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.OIOEUReferenceDataHentIType;
import oio.skat.emcs.ws._1_0.OIOEUReferenceDataHentOType;
import oio.skat.emcs.ws._1_0_1.OIOEUReferenceDataHentService;
import oio.skat.emcs.ws._1_0_1.OIOEUReferenceDataHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;
import java.util.logging.Logger;

public class OIOEUReferenceDataHentClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOEUReferenceDataHentClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOEUReferenceDataHentClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOEUReferenceDataHent service
     */
    public OIOEUReferenceDataHentClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param beskedIdentifikator Id of message
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOEUReferenceDataHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator,
                       String beskedIdentifikator) throws DatatypeConfigurationException {

        OIOEUReferenceDataHentIType request = new OIOEUReferenceDataHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        request.setBeskedIdentifikator(beskedIdentifikator);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOEUReferenceDataHentService service = new OIOEUReferenceDataHentService();
        OIOEUReferenceDataHentServicePortType port = service.getOIOEUReferenceDataHentServicePort();

        // Set endpoint of service.
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpointURL);
        addCleartextLogging(ClientProxy.getClient(port));

        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append(generateConsoleOutput(
                request.getHovedOplysninger(),
                request.getVirksomhedIdentifikationStruktur().getAfgiftOperatoerPunktAfgiftIdentifikator(),
                request.getVirksomhedIdentifikationStruktur().getIndberetter().getVirksomhedSENummerIdentifikator()
        ));
        LOGGER.info(NEW_LINE + sbRequest.toString());

        OIOEUReferenceDataHentOType response = port.getOIOEUReferenceDataHent(request);


        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));


        LOGGER.info(NEW_LINE + sb.toString());
        String ie733 = prettyFormatDocument(response.getIE733BeskedTekst(), 2, true);
        LOGGER.info("IE733:");
        LOGGER.info(ie733);
        return response;
    }


}
