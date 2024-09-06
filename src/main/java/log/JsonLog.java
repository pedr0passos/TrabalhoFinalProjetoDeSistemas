package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author 
 * Pedro Henrique Passos
 * Catterina Salvador
 */

public class JsonLog {
    public void gravarLogJson(String operacao) throws FileNotFoundException {
        File file = createFile();
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(file, true));
            myWriter.write("{ \"log\": { ");
            myWriter.write("\"dataHora\" :" + "\"" + LocalDateTime.now().toString() + "\"" + ",");
            myWriter.write("\t\"operacao\" :" + "\"" + operacao + "\" } } ");
            myWriter.write("\n");
            myWriter.close();
            System.out.println("Arquivo escrito com Sucesso.");
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    public File createFile() {
        try {
            File json = new File("src\\main\\java\\registros\\JsonLog.json");
            System.out.println(json.getAbsolutePath());
            if (json.createNewFile()) {
                System.out.println("Arquivo criado: " + json.getName());
                return json;
            } else {
                System.out.println("Arquivo ja existe.");
                return json;
            }
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
            return null;
        }
    }
}
