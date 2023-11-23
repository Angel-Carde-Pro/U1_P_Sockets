package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    String serverIP;
    Socket cliente;

    public static void main(String[] args) {
        Cliente obj1 = new Cliente();
        obj1.iniciarSocketCliente();
    }

    public void iniciarSocketCliente() {
        try {
            serverIP = "127.0.0.1";
            cliente = new Socket(serverIP, 5555);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader serverResponseReader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            boolean exit = false;
            String respuesta;

            do {
                try {
                    System.out.print("Por favor, introduce la placa.");
                    String placa = reader.readLine();

                    writer.println(placa);

                    String respuestaServidor = serverResponseReader.readLine();
                    System.out.println("Respuesta del servidor: " + respuestaServidor);

                    System.out.print("¿Desea buscar otra palabra? (S/N): ");
                    respuesta = reader.readLine();
                } catch (IOException e) {
                    // Manejar la excepción y salir del bucle
                    e.printStackTrace();
                    break;
                }
            } while (respuesta.equalsIgnoreCase("S"));
            
            System.out.println("---- HASTA LUEGO ----");

            reader.close();
            writer.close();
            serverResponseReader.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
