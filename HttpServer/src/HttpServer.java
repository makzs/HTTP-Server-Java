import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpServer {
    public static void main(String[] args) throws IOException {

        // cria uma server socket na porta 8080 que vai rodar localmente enquanto o loop
        // rodar
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("iniciando server...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    // adiciona um leitor para ler a requisição HTTP e relaciona ela com o path da pagina
                    BufferedReader leitor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String requestLine = leitor.readLine();
                    String path = requestLine.split(" ")[1];

                    // se o caminho (path) da pagina for igual a "/" ele vai abrir uma pagina principal
                    if (path.equals("/")) {
                        Date hoje = new Date();

                        // Responde com a confirmação que foi conectado no horario de hoje
                        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                                "Conectado " + hoje;
                        clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                    } 
                    // se o caminho (path) da pagina for igual a /data ele vai retornar um JSON teste
                    else if (path.equals("/data")) {
                        String jsonResponse = "{ \"TesteJson\": \"Este é um dado em JSON\" }";
                        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: application/json\r\n" +
                                "Content-Length: " + jsonResponse.length() + "\r\n" +
                                "\r\n" +
                                jsonResponse;
                        clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                    } 
                    // se ele nao for nenhuma nem outra ira retornar um erro 404
                    else {
                        String httpResponse = "HTTP/1.1 404 Not Found\r\n\r\n" +
                                              "Página não encontrada";
                        clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                    }
                }
            }
        }
    }
}
