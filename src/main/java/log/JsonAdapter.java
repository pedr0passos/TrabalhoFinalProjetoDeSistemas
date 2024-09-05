package log;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 
 * Pedro Henrique Passos Rocha
 * Catterina Salvador
 */

public class JsonAdapter implements Log {
    
    private JsonLog jsonLog;

    public JsonAdapter(JsonLog jsonLog) {
        this.jsonLog = jsonLog;
    }

    @Override
    public void gravarLog(String operacao) {
        try {
            jsonLog.gravarLogJson(operacao);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
