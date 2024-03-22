package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.JAXBException;
import org.springframework.binding.message.MessageContext;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Serializable;

public class ServiceController implements Serializable {

    private OIOBeskedAfvisningSamlingHentClient oioBeskedAfvisningSamlingHentClient;
    private OIOEksportAfvisningSamlingHentClient oioEksportAfvisningSamlingHentClient;
    private OIOEksportAngivelseInvalideringNotifikationSamlingHentClient oioEksportAngivelseInvalideringNotifikationSamlingHentClient;
    private OIOEksportGodkendelseSamlingHentClient oioEksportGodkendelseSamlingHentClient;
    private OIOForsendelseAfbrydelseBeskedSamlingHentClient oioForsendelseAfbrydelseBeskedSamlingHentClient;
    private OIOHaendelseRapportSamlingHentClient oioHaendelseRapportSamlingHentClient;
    private OIOLedsageDokumentAnnulleringSamlingHentClient oioLedsageDokumentAnnulleringSamlingHentClient;
    private OIOLedsageDokumentDestinationSkiftSamlingHentClient oioLedsageDokumentDestinationSkiftSamlingHentClient;
    private OIOLedsageDokumentNotifikationSamlingHentClient oioLedsageDokumentNotifikationSamlingHentClient;
    private OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient oioLedsageDokumentOmdirigeretAdvisSamlingHentClient;
    private OIOLedsageDokumentSamlingHentClient oioLedsageDokumentSamlingHentClient;
    private OIOPaamindelseSamlingHentClient oioPaamindelseSamlingHentClient;
    private OIOLedsageDokumentOpretClient oioLedsageDokumentOpretClient;
    private OIOLedsageDokumentAnnulleringOpretClient oioLedsageDokumentAnnulleringOpretClient;
    private OIOLedsageDokumentDestinationSkiftOpretClient oioLedsageDokumentDestinationSkiftOpretClient;
    private OIOLedsageDokumentNotifikationOpretClient oioLedsageDokumentNotifikationOpretClient;
    private OIOLedsageDokumentOpsplitningOpretClient oioLedsageDokumentOpsplitningOpretClient;
    private OIOKvitteringSamlingHentClient oioKvitteringSamlingHentClient;
    private OIOForsinkelseForklaringOpretClient oioForsinkelseForklaringOpretClient;
    private OIOKvitteringAfvigelseBegrundelseOpretClient oioKvitteringAfvigelseBegrundelseOpretClient;
    private OIOKvitteringOpretClient oioKvitteringOpretClient;
    private OIOEUReferenceDataAnmodClient oioeuReferenceDataAnmodClient;
    private OIOEUReferenceDataHentClient oioeuReferenceDataHentClient;


    public String makeServiceCall(SamlingHentModel model, MessageContext context) throws DatatypeConfigurationException, JAXBException, ParserConfigurationException, IOException, SAXException {
        switch (model.getServiceName()) {
            case "OIOBeskedAfvisningSamlingHentClient":
                if (this.oioBeskedAfvisningSamlingHentClient == null) {
                    this.oioBeskedAfvisningSamlingHentClient = new OIOBeskedAfvisningSamlingHentClient(null);
                }
                return this.oioBeskedAfvisningSamlingHentClient.invoke(model, context);

            case "OIOEksportAfvisningSamlingHentClient":
                if (this.oioEksportAfvisningSamlingHentClient == null) {
                    this.oioEksportAfvisningSamlingHentClient  = new OIOEksportAfvisningSamlingHentClient(null);
                }
                return this.oioEksportAfvisningSamlingHentClient.invoke(model, context);

            case "OIOEksportAngivelseInvalideringNotifikationSamlingHentClient":
                if (this.oioEksportAngivelseInvalideringNotifikationSamlingHentClient == null) {
                    this.oioEksportAngivelseInvalideringNotifikationSamlingHentClient = new OIOEksportAngivelseInvalideringNotifikationSamlingHentClient(null);
                }
                return this.oioEksportAngivelseInvalideringNotifikationSamlingHentClient.invoke(model, context);

            case "OIOEksportGodkendelseSamlingHentClient":
                if (this.oioEksportGodkendelseSamlingHentClient == null) {
                    this.oioEksportGodkendelseSamlingHentClient = new OIOEksportGodkendelseSamlingHentClient(null);
                }
                return this.oioEksportGodkendelseSamlingHentClient.invoke(model, context);

            case "OIOForsendelseAfbrydelseBeskedSamlingHentClient":
                if (this.oioForsendelseAfbrydelseBeskedSamlingHentClient == null) {
                    this.oioForsendelseAfbrydelseBeskedSamlingHentClient = new OIOForsendelseAfbrydelseBeskedSamlingHentClient(null);
                }
                return this.oioForsendelseAfbrydelseBeskedSamlingHentClient.invoke(model, context);

            case "OIOHaendelseRapportSamlingHentClient":
                if (this.oioHaendelseRapportSamlingHentClient == null) {
                    this.oioHaendelseRapportSamlingHentClient = new OIOHaendelseRapportSamlingHentClient(null);
                }
                return this.oioHaendelseRapportSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentAnnulleringSamlingHentClient":
                if (this.oioLedsageDokumentAnnulleringSamlingHentClient == null) {
                    this.oioLedsageDokumentAnnulleringSamlingHentClient = new OIOLedsageDokumentAnnulleringSamlingHentClient(null);
                }
                return this.oioLedsageDokumentAnnulleringSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentDestinationSkiftSamlingHentClient":
                if (this.oioLedsageDokumentDestinationSkiftSamlingHentClient == null) {
                    this.oioLedsageDokumentDestinationSkiftSamlingHentClient = new OIOLedsageDokumentDestinationSkiftSamlingHentClient(null);
                }
                return this.oioLedsageDokumentDestinationSkiftSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentNotifikationSamlingHentClient":
                if (this.oioLedsageDokumentNotifikationSamlingHentClient == null) {
                    this.oioLedsageDokumentNotifikationSamlingHentClient = new OIOLedsageDokumentNotifikationSamlingHentClient(null);
                }
                return this.oioLedsageDokumentNotifikationSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient":
                if (this.oioLedsageDokumentOmdirigeretAdvisSamlingHentClient == null) {
                    this.oioLedsageDokumentOmdirigeretAdvisSamlingHentClient = new OIOLedsageDokumentOmdirigeretAdvisSamlingHentClient(null);
                }
                return this.oioLedsageDokumentOmdirigeretAdvisSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentSamlingHentClient":
                if (this.oioLedsageDokumentSamlingHentClient == null) {
                    this.oioLedsageDokumentSamlingHentClient = new OIOLedsageDokumentSamlingHentClient(null);
                }
                return this.oioLedsageDokumentSamlingHentClient.invoke(model, context);

            case "OIOPaamindelseSamlingHentClient":
                if (this.oioPaamindelseSamlingHentClient == null) {
                    this.oioPaamindelseSamlingHentClient = new OIOPaamindelseSamlingHentClient(null);
                }
                return this.oioPaamindelseSamlingHentClient.invoke(model, context);

            case "OIOLedsageDokumentOpretClient":
                if (this.oioLedsageDokumentOpretClient == null) {
                    this.oioLedsageDokumentOpretClient = new OIOLedsageDokumentOpretClient(null);
                }
                return this.oioLedsageDokumentOpretClient.invoke(model, context);

            case "OIOLedsageDokumentAnnulleringOpretClient":
                if (this.oioLedsageDokumentAnnulleringOpretClient == null) {
                    this.oioLedsageDokumentAnnulleringOpretClient = new OIOLedsageDokumentAnnulleringOpretClient(null);
                }
                return this.oioLedsageDokumentAnnulleringOpretClient.invoke(model, context);

            case "OIOLedsageDokumentDestinationSkiftOpretClient":
                if (this.oioLedsageDokumentDestinationSkiftOpretClient == null) {
                    this.oioLedsageDokumentDestinationSkiftOpretClient = new OIOLedsageDokumentDestinationSkiftOpretClient(null);
                }
                return this.oioLedsageDokumentDestinationSkiftOpretClient.invoke(model, context);

            case "OIOLedsageDokumentNotifikationOpretClient":
                if (this.oioLedsageDokumentNotifikationOpretClient == null) {
                    this.oioLedsageDokumentNotifikationOpretClient = new OIOLedsageDokumentNotifikationOpretClient(null);
                }
                return this.oioLedsageDokumentNotifikationOpretClient.invoke(model, context);

            case "OIOLedsageDokumentOpsplitningOpretClient":
                if (this.oioLedsageDokumentOpsplitningOpretClient == null) {
                    this.oioLedsageDokumentOpsplitningOpretClient = new OIOLedsageDokumentOpsplitningOpretClient(null);
                }
                return this.oioLedsageDokumentOpsplitningOpretClient.invoke(model, context);

            case "OIOKvitteringSamlingHentClient":
                if (this.oioKvitteringSamlingHentClient == null) {
                    this.oioKvitteringSamlingHentClient = new OIOKvitteringSamlingHentClient(null);
                }
                return this.oioKvitteringSamlingHentClient.invoke(model, context);

            case "OIOForsinkelseForklaringOpretClient":
                if (this.oioForsinkelseForklaringOpretClient == null) {
                    this.oioForsinkelseForklaringOpretClient = new OIOForsinkelseForklaringOpretClient(null);
                }
                return this.oioForsinkelseForklaringOpretClient.invoke(model, context);

            case "OIOKvitteringAfvigelseBegrundelseOpretClient":
                if (this.oioKvitteringAfvigelseBegrundelseOpretClient == null) {
                    this.oioKvitteringAfvigelseBegrundelseOpretClient = new OIOKvitteringAfvigelseBegrundelseOpretClient(null);
                }
                return this.oioKvitteringAfvigelseBegrundelseOpretClient.invoke(model, context);

            case "OIOKvitteringOpretClient":
                if (this.oioKvitteringOpretClient == null) {
                    this.oioKvitteringOpretClient = new OIOKvitteringOpretClient(null);
                }
                return this.oioKvitteringOpretClient.invoke(model, context);

            case "OIOEUReferenceDataAnmodClient":
                if (this.oioeuReferenceDataAnmodClient == null) {
                    this.oioeuReferenceDataAnmodClient = new OIOEUReferenceDataAnmodClient(null);
                }
                return this.oioeuReferenceDataAnmodClient.invoke(model, context);

            case "OIOEUReferenceDataHentClient":
                if (this.oioeuReferenceDataHentClient == null) {
                    this.oioeuReferenceDataHentClient = new OIOEUReferenceDataHentClient(null);
                }
                return this.oioeuReferenceDataHentClient.invoke(model, context);

            default:
                return "Error in service call";
        }
    }
}
