import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HttpServer {
    public static void main(String[] args) throws IOException {

        // cria uma server socket na porta 8080 que vai rodar localmente enquanto o loop rodar
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("iniciando server...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    Date hoje = new Date();

                    // Responde com a confirmação que foi conectado no horario de hoje
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                                          "Conectado " + hoje;
                                          clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
            }
        }
    }
}
