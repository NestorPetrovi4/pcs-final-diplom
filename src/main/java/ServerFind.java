import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFind {
    public static void serverStart(int port, String path, String word) throws IOException {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File(path));
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept(); PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String words = in.readLine();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    var pageEntry = engine.search(words);
                    if (pageEntry == null) {
                        out.println("Ничего не удалось найти");
                    } else {
                        out.println(gson.toJson(pageEntry));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Сервер запустить не удалось!!!");
            e.printStackTrace();
        }
    }
}