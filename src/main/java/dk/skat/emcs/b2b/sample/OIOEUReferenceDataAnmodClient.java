package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import oio.skat.emcs.ws._1_0.*;
import oio.skat.emcs.ws._1_0_1.OIOEUReferenceDataAnmodService;
import oio.skat.emcs.ws._1_0_1.OIOEUReferenceDataAnmodServicePortType;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

public class OIOEUReferenceDataAnmodClient extends EMCSBaseClient {

    private static final Logger LOGGER = Logger.getLogger(OIOEUReferenceDataAnmodClient.class.getName());

    private String endpointURL;

    /**
     * Private constructor
     */
    private OIOEUReferenceDataAnmodClient() {
    }

    /**
     * Constructor
     *
     * @param endpointURL Endpoint of OIOEUReferenceDataAnmod service
     */
    public OIOEUReferenceDataAnmodClient(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * @param virksomhedSENummerIdentifikator         VAT number of entity calling entity
     * @param afgiftOperatoerPunktAfgiftIdentifikator Excise Number of calling entity
     * @param path                                    Path to IE705 Document
     * @return
     * @throws DatatypeConfigurationException
     */
    public OIOEUReferenceDataAnmodOType invoke(String virksomhedSENummerIdentifikator,
                                               String afgiftOperatoerPunktAfgiftIdentifikator,
                                               String path,
                                               String beskedIdentifikator) throws DatatypeConfigurationException, ParserConfigurationException, SAXException, IOException {

        OIOEUReferenceDataAnmodIType request = new OIOEUReferenceDataAnmodIType();
        request.setHovedOplysninger(generateHovedOplysningerType());
        request.setVirksomhedIdentifikationStruktur(generateVirksomhedIdentifikationStrukturType(virksomhedSENummerIdentifikator, afgiftOperatoerPunktAfgiftIdentifikator));
        IE705StrukturType ie705StrukturType = new IE705StrukturType();
        Document ie705 = loadIEDocument(path);
        resetTimeOfPreparation(ie705, "/IE705/Header/TimeOfPreparation");
        resetDateOfPreparation(ie705, "/IE705/Header/DateOfPreparation");
        resetMessageIdentifier(ie705, "/IE705/Header/MessageIdentifier", beskedIdentifikator);
        ie705StrukturType.setAny(ie705.getDocumentElement());
        request.setIE705Struktur(ie705StrukturType);

        Bus bus = new SpringBusFactory().createBus("emcs-policy.xml", false);
        BusFactory.setDefaultBus(bus);
        OIOEUReferenceDataAnmodService service = new OIOEUReferenceDataAnmodService();
        OIOEUReferenceDataAnmodServicePortType port = service.getOIOEUReferenceDataAnmodServicePort();

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
        LOGGER.info(prettyFormatDocument(ie705, 2, true));

        OIOEUReferenceDataAnmodOType response = port.getOIOEUReferenceDataAnmod(request);
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(response.getHovedOplysningerSvar()));

        LOGGER.info(NEW_LINE + sb.toString());
        return response;
    }

    public String invoke(SamlingHentModel samlingHentModel) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException, JAXBException {
        if (this.endpointURL == null){
            this.endpointURL = getEndpoint("OIOEUReferenceDataAnmod");
        }
        OIOEUReferenceDataAnmodOType result = invoke(samlingHentModel.getVirksomhedSENummerIdentifikator(), samlingHentModel.getAfgiftOperatoerPunktAfgiftIdentifikator(), samlingHentModel.getFile(), samlingHentModel.getARCnumber());
        return SamlingHentMashalling.toString(result,"urn:oio:skat:emcs:ws:1.0.1","OIOEUReferenceDataAnmod_O");
    }


}
