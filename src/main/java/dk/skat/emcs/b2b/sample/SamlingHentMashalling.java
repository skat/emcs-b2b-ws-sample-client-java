package dk.skat.emcs.b2b.sample;

import jakarta.xml.bind.*;
import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentIType;
import oio.skat.emcs.ws._1_0.OIOBeskedAfvisningSamlingHentOType;

import javax.xml.namespace.QName;
import java.io.InputStream;
import java.io.StringWriter;

public class SamlingHentMashalling {

    public static <T> String toString(T document, String nameSpaceURI, String localPart) throws JAXBException {
        try {
            JAXBElement<T> jaxbElement
                = new JAXBElement<T>( new QName(nameSpaceURI, localPart), (Class<T>) document.getClass(), document);

            JAXBContext jc = JAXBContext.newInstance(document.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter w = new StringWriter();
            marshaller.marshal(jaxbElement, w);
            return w.toString();
        } catch (JAXBException e) {
            throw e;
        }
    }
}
