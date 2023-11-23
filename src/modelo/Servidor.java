
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    private static final Map<String, String> placaCiudadMap = new HashMap<>();
    ServerSocket serverSocket;

    static {
        placaCiudadMap.put("A", "Azuay");
        placaCiudadMap.put("B", "Bolívar");
        placaCiudadMap.put("U", "Cañar");
        placaCiudadMap.put("X", "Cotopaxi");
        placaCiudadMap.put("H", "Chimborazo");
        placaCiudadMap.put("O", "El Oro");
        placaCiudadMap.put("E", "Esmeraldas");
        placaCiudadMap.put("Q", "Francisco de Orellana");
        placaCiudadMap.put("W", "Galápagos");
        placaCiudadMap.put("G", "Guayas");
        placaCiudadMap.put("I", "Imbabura");
        placaCiudadMap.put("L", "Loja");
        placaCiudadMap.put("R", "Los Ríos");
        placaCiudadMap.put("M", "Manabí");
        placaCiudadMap.put("V", "Morona Santiago");
        placaCiudadMap.put("N", "Napo");
        placaCiudadMap.put("S", "Pastaza");
        placaCiudadMap.put("P", "Pichincha");
        placaCiudadMap.put("Y", "Santa Elena");
        placaCiudadMap.put("J", "Santo Domingo de los Tsáchilas");
        placaCiudadMap.put("K", "Sucumbíos");
        placaCiudadMap.put("T", "Tungurahua");
        placaCiudadMap.put("Z", "Zamora Chinchipe");
    }

    public static void main(String[] args) {
        Servidor obj1 = new Servidor();
        obj1.iniciarSocketServidor(5555);
    }

    public void iniciarSocketServidor(int puerto) {
        System.out.println("---- PRUEBA U1 - ANGEL CÁRDENAS - M5A ----\n");
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Esperando conexiones en el puerto: " + puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            boolean salir = false;

            while (!salir) {
                try {
                    String placa = reader.readLine();
                    System.out.println(" ---- Placa recibida: " + placa);

                    if (placa.length() > 0) {
                        String primeraLetra = String.valueOf(placa.charAt(0));

                        String ciudad = placaCiudadMap.getOrDefault(primeraLetra, "Desconocida");

                        if ("Desconocida".equals(ciudad)) {
                            writer.println("Respuesta incorrecta. Por favor, intente nuevamente.");
                        } else {
                            System.out.println(" Ciudad correspondiente: " + ciudad);
                            writer.println(ciudad);
                        }
                    } else {
                        writer.println("Respuesta incorrecta. Por favor, intente nuevamente.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    salir = true;
                }
            }
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
