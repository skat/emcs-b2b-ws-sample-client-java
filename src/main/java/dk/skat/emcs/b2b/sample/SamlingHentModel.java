package dk.skat.emcs.b2b.sample;

import java.io.File;
import java.io.Serializable;

public class SamlingHentModel implements Serializable {
    private String virksomhedSENummerIdentifikator;
    private String afgiftOperatoerPunktAfgiftIdentifikator;
    private String serviceName;
    private String ARCnumber;
    private String file;

    public String getVirksomhedSENummerIdentifikator() {
        return virksomhedSENummerIdentifikator;
    }

    public void setVirksomhedSENummerIdentifikator(String virksomhedSENummerIdentifikator) {
        this.virksomhedSENummerIdentifikator = virksomhedSENummerIdentifikator;
    }

    public String getAfgiftOperatoerPunktAfgiftIdentifikator() {
        return afgiftOperatoerPunktAfgiftIdentifikator;
    }

    public void setAfgiftOperatoerPunktAfgiftIdentifikator(String afgiftOperatoerPunktAfgiftIdentifikator) {
        this.afgiftOperatoerPunktAfgiftIdentifikator = afgiftOperatoerPunktAfgiftIdentifikator;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getARCnumber() {
        return ARCnumber;
    }

    public void setARCnumber(String ARCnumber) {
        this.ARCnumber = ARCnumber;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
