package log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 
 * Pedro Henrique Passos
 * Catterina Salvador
 */

public class JsonLog {

    private static final String FILE_PATH = "src\\main\\java\\logs\\JsonLog.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void gravarLogJson(String operacao, String nome, String usuario) throws IOException {
        File file = createFile();
        String novoLog = criarLogEntry(operacao, nome, usuario);

        // Ler o conteúdo existente do arquivo JSON
        StringBuilder conteudoAtual = new StringBuilder();
        if (file.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    conteudoAtual.append(linha).append("\n");
                }
            }

            // Remover o último colchete e a nova linha para adicionar o novo log
            int index = conteudoAtual.lastIndexOf("\n]}");
            if (index != -1) {
                conteudoAtual.delete(index, conteudoAtual.length());
                conteudoAtual.append(",\n").append(novoLog).append("\n]}");
            }
        } else {
            conteudoAtual.append("{\"logs\": [\n").append(novoLog).append("\n]}");
        }

        // Escrever o conteúdo atualizado no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(conteudoAtual.toString());
        }
    }

    private String criarLogEntry(String operacao, String nome, String usuario) {
        String dataHora = LocalDateTime.now().format(DATE_FORMATTER);
        return String.format(
            "{ \"dataHora\": \"%s\", \"operacao\": \"%s\", \"nome\": \"%s\", \"usuario\": \"%s\" }",
            dataHora, operacao, nome, usuario
        );
    }

    public File createFile() {
        try {
            File jsonFile = new File(FILE_PATH);
            jsonFile.createNewFile();
            return jsonFile;
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo JSON.");
            e.printStackTrace();
            return null;
        }
    }
}
