package log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 
 * Pedro Henrique Passos
 * Catterina Salvador
 * João Victor Mascarenhas
 */

public class CsvLog {

    private static final String FILE_PATH = "src\\main\\java\\logs\\Log.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void gravarLogCsv(String operacao, String nome, String usuario) throws IOException {
        File file = createFile();
        String novoLog = criarLogEntry(operacao, nome, usuario);

        // Escrever o conteúdo atualizado no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(novoLog);
            writer.newLine();
        }
    }

    private String criarLogEntry(String operacao, String nome, String usuario) {
        String dataHora = LocalDateTime.now().format(DATE_FORMATTER);
        return String.format("\"%s\"; \"%s\"; \"%s\"; \"%s\"",
            dataHora, operacao, nome, usuario
        );
    }

    public File createFile() {
        try {
            File csvFile = new File(FILE_PATH);
            csvFile.createNewFile();
            return csvFile;
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo CSV.");
            e.printStackTrace();
            return null;
        }
    }
}
