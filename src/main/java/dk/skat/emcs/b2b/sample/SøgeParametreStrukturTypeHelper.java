package dk.skat.emcs.b2b.sample;

import oio.skat.emcs.ws._1_0.SøgeParametreStrukturType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SøgeParametreStrukturTypeHelper {

    public static SøgeParametreStrukturType getSøgeParametreStrukturType(Date startDate, Date endDate){
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning gyldighedPeriodeUdsøgning
                = new SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning();
        soegeParametre.setGyldighedPeriodeUdsøgning(gyldighedPeriodeUdsøgning);
        try {
            gyldighedPeriodeUdsøgning.setStartDate(getXMLGregorianCalendar(startDate));
            gyldighedPeriodeUdsøgning.setEndDate(getXMLGregorianCalendar(endDate));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    private static XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static SøgeParametreStrukturType getSøgeParametreStrukturType(Integer interval) {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning gyldighedPeriodeUdsøgning
                = new SøgeParametreStrukturType.SøgeParametre.GyldighedPeriodeUdsøgning();
        soegeParametre.setGyldighedPeriodeUdsøgning(gyldighedPeriodeUdsøgning);

        // Search for messages in the period: now minus 1 month -- to -- now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -interval);
        Date startDate = cal.getTime();
        try {
            gyldighedPeriodeUdsøgning.setStartDate(getXMLGregorianCalendar(startDate));
            gyldighedPeriodeUdsøgning.setEndDate(getXMLGregorianCalendar(new Date()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

    public static SøgeParametreStrukturType getSøgeParametreStrukturType(String arcnumber) {
        SøgeParametreStrukturType soegeParametreStrukturType = new SøgeParametreStrukturType();
        SøgeParametreStrukturType.SøgeParametre soegeParametre = new SøgeParametreStrukturType.SøgeParametre();
        soegeParametre.setLedsagedokumentARCIdentifikator(arcnumber);
        soegeParametreStrukturType.setSøgeParametre(soegeParametre);
        return soegeParametreStrukturType;
    }

}
