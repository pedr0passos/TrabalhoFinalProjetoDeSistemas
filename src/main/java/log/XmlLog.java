
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

public class XmlLog {

    public void gravarLogXml(String operacao) throws FileNotFoundException {
        File file = createFile();
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(file, true));
            myWriter.write("<log>\n");
            myWriter.write("\t<dataHora>" + LocalDateTime.now().toString() + "</dataHora>\n");
            myWriter.write("\t<operacao>" + operacao + "</operacao>\n");
            myWriter.write("</log>\n");
            myWriter.close();
            System.out.println("Arquivo escrito com Sucesso.");
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    public File createFile() {
        try {
            File xml = new File("src\\main\\java\\registros","xmlLog.xml");
            System.out.println(xml.getAbsolutePath());
            if (xml.createNewFile()) {
                System.out.println("Arquivo criado: " + xml.getName());
                return xml;
            } else {
                System.out.println("Arquivo ja existe.");
                return xml;
            }
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
            return null;
        }
    }
}
