package dk.skat.emcs.b2b.sample;

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
}
