package log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void gravarLog(String operacao, String nome, String usuario, boolean sucesso, String mensagemFalha) {
        try {
            if (sucesso) {
                jsonLog.gravarLogJson(operacao, nome, usuario);
            } else {
                jsonLog.gravarLogJson("Erro: " + operacao, nome, usuario);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
