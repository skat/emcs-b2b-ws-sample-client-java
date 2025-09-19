package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOEksportAfvisningSamlingHentService;
import oio.skat.emcs.ws._1_0_1.OIOEksportAfvisningSamlingHentServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;
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
                                                      String afgiftOperatoerPunktAfgiftIdentifikator
                                                      ) throws DatatypeConfigurationException {
        return invoke(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator, null);
    }

    /**
     *
     * @param virksomhedSENummerIdentifikator VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOEksportAfvisningSamlingHentOType invoke(String virksomhedSENummerIdentifikator,
                       String afgiftOperatoerPunktAfgiftIdentifikator, SøgeParametreStrukturType spst) throws DatatypeConfigurationException {

        OIOEksportAfvisningSamlingHentIType request = new OIOEksportAfvisningSamlingHentIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        if (spst != null) {
            request.setSøgeParametreStruktur(spst);
        } else {
            request.setSøgeParametreStruktur(getSøgeParametreStrukturType(10));
        }
        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOEksportAfvisningSamlingHentService service = new OIOEksportAfvisningSamlingHentService();
        OIOEksportAfvisningSamlingHentServicePortType port = service.getOIOEksportAfvisningSamlingHentServicePort();

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

        OIOEksportAfvisningSamlingHentOType response = port.getOIOEksportAfvisningSamlingHent(request);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));
        if (response.getEksportAfvisningSamling() != null) {
            sb.append(generateConsoleOutput(response.getEksportAfvisningSamling().getIE839BeskedTekst(), "IE839"));
        }
        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }


}
