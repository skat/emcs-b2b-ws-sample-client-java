package dk.skat.emcs.b2b.sample;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Credits/Based on + with a few modifications: https://stackoverflow.com/questions/40019208/how-to-enable-debug-logging-in-apache-cxf-before-encrypting
 */
public class CleartextLogger extends AbstractSoapInterceptor {
    private static final String LOG_SETUP = CleartextLogger.class.getName() + ".log-setup";
    private Logger logger;

    public CleartextLogger() {
        super(Phase.POST_PROTOCOL);
        this.logger = LogUtils.getLogger(CleartextLogger.class);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            boolean logged = message.containsKey(LOG_SETUP);
            if (!logged) {
                message.put(LOG_SETUP, Boolean.TRUE);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                SOAPMessage smsg = message.getContent(SOAPMessage.class);
                if (smsg != null) {
                    smsg.writeTo(bout);
                    log(bout.toString());
                }
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String xml) {
        StringReader in = new StringReader(xml);
        StringWriter swriter = new StringWriter();
        XMLStreamWriter xwriter = StaxUtils.createXMLStreamWriter(swriter);
        xwriter = new PrettyPrintXMLStreamWriter(xwriter, 2);
        try {
            StaxUtils.copy(new StreamSource(in), xwriter);
        } catch (XMLStreamException xse) {
            //ignore
        } finally {
            try {
                xwriter.flush();
                xwriter.close();
            } catch (XMLStreamException xse2) {
                //ignore
            }
            in.close();
        }

        String result = swriter.toString();
        logger.info(result);
    }
}