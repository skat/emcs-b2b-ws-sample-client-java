package dk.skat.emcs.b2b.sample;

import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.AdvisStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.FejlStrukturType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerSvarType;
import dk.oio.rep.skat_dk.basis.kontekst.xml.schemas._2006._09._01.HovedOplysningerType;
import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * EMCSBaseClient
 *
 * @author SKAT
 * @since 1.2
 */
public class EMCSBaseClient {

    protected static final String NEW_LINE = System.getProperty("line.separator");

    protected SøgeParametreStrukturType getSøgeParametreStrukturType(Integer interval) throws DatatypeConfigurationException {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning gyldighedPeriodeUdsøgning
                = new SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning();
        soegeParametre.setGyldighedPeriodeUdsøgning(gyldighedPeriodeUdsøgning);

        // Search for messages in the period: now minus 1 month -- to -- now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -interval);
        Date startDate = cal.getTime();
        gyldighedPeriodeUdsøgning.setStartDate(getXMLGregorianCalendar(startDate));
        gyldighedPeriodeUdsøgning.setEndDate(getXMLGregorianCalendar(new Date()));
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    protected SøgeParametreStrukturType getSøgeParametreStrukturType(String arcnumber) throws DatatypeConfigurationException {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        soegeParametre.setLedsagedokumentARCIdentifikator(arcnumber);
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    /**
     * Generate output of HovedOplysningerType document for console
     *
     * @param hovedOplysningerType HovedOplysningerType document (found in service response)
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerType hovedOplysningerType) {
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(NEW_LINE);
        sb.append("** HovedOplysninger").append(NEW_LINE);
        sb.append("**** Transaction Id: ").append(hovedOplysningerType.getTransaktionIdentifikator()).append(NEW_LINE);
        sb.append("**** Transaction Time: ").append(hovedOplysningerType.getTransaktionTid()).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        return sb.toString();
    }

    /**
     * Generate output of HovedOplysningerType document for console together with fields:
     *
     * AfgiftOperatoerPunktAfgiftIdentifikator
     * VirksomhedSENummerIdentifikator
     *
     * @param hovedOplysningerType HovedOplysningerType document (found in service response)
     * @param afgiftOperatoerPunktAfgiftIdentifikator
     * @param virksomhedSENummerIdentifikator
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerType hovedOplysningerType,
                                           String afgiftOperatoerPunktAfgiftIdentifikator,
                                           String virksomhedSENummerIdentifikator) {
        StringBuilder sb = new StringBuilder();
        sb.append(generateConsoleOutput(hovedOplysningerType));
        sb.append("** VirksomhedIdentifikationStruktur").append(NEW_LINE);
        sb.append("**** AfgiftOperatoerPunktAfgiftIdentifikator: ").append(afgiftOperatoerPunktAfgiftIdentifikator).append(NEW_LINE);
        sb.append("**** VirksomhedSENummerIdentifikator: ").append(virksomhedSENummerIdentifikator).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        return sb.toString();
    }

    /**
     * Generate out of HovedOplysningerSvarType document for console
     *
     * @param hovedOplysningerSvarType HovedOplysningerSvarType document (found in service response)
     * @return String formatted for console output
     */
    protected String generateConsoleOutput(HovedOplysningerSvarType hovedOplysningerSvarType) {
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append(NEW_LINE);
        sb.append("** HovedOplysningerSvar").append(NEW_LINE);
        sb.append("**** Transaction Id: ").append(hovedOplysningerSvarType.getTransaktionIdentifikator()).append(NEW_LINE);
        sb.append("**** Transaction Time: ").append(hovedOplysningerSvarType.getTransaktionTid()).append(NEW_LINE);
        sb.append("**** Service Identification: ").append(hovedOplysningerSvarType.getServiceIdentifikator()).append(NEW_LINE);
        sb.append("*******************************************************************").append(NEW_LINE);
        if (hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur().size() > 0) {
            for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
                if (errorOrAdvis instanceof FejlStrukturType) {
                    FejlStrukturType fejlStrukturType = (FejlStrukturType) errorOrAdvis;
                    sb.append("**** Error").append(NEW_LINE);
                    sb.append("****** Error Code: ").append(fejlStrukturType.getFejlIdentifikator()).append(NEW_LINE);
                    sb.append("****** Error Text: ").append(fejlStrukturType.getFejlTekst()).append(NEW_LINE);
                }
                if (errorOrAdvis instanceof AdvisStrukturType) {
                    AdvisStrukturType advisStrukturType = (AdvisStrukturType) errorOrAdvis;
                    sb.append("**** Advis").append(NEW_LINE);
                    sb.append("****** Advis Code: ").append(advisStrukturType.getAdvisIdentifikator()).append(NEW_LINE);
                    sb.append("****** Advis Text: ").append(advisStrukturType.getAdvisTekst()).append(NEW_LINE);
                }
            }
            sb.append("*******************************************************************").append(NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * Test if header object contains at least one error.
     *
     * @param hovedOplysningerSvarType HovedOplysningerSvarType document (found in service response)
     * @return True if at least one error.
     */
    protected boolean hasError(HovedOplysningerSvarType hovedOplysningerSvarType) {
        boolean hasError = false;
        for (Object errorOrAdvis : hovedOplysningerSvarType.getSvarStruktur().getAdvisStrukturOrFejlStruktur()) {
            if (errorOrAdvis instanceof FejlStrukturType) {
                hasError = true;
            }
        }
        return hasError;
    }

}
