package log;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 
 * Pedro Henrique Passos Rocha
 * Catterina Salvador
 */

public class XmlAdapter implements Log{
    
    private XmlLog xmlLog;

    public XmlAdapter(XmlLog xmlLog) {
        this.xmlLog = xmlLog;
    }

    @Override
    public void gravarLog(String operacao) {
        try {
            xmlLog.gravarLogXml(operacao);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XmlAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
