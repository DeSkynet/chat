package servidor;

import java.net.Socket;

public class MainServidor {
    public static void main(String[] args) {
        Socket socket = null;

        Servidor servidor = null;
        int puerto = 3000;
        int maximoConexiones = 10;

        System.out.println("SERVIDOR DE CHAT POR CONSOLA:\n");
     

        try {
            puerto = 3000;
        } catch (Exception e) {
            System.out.println("Error al ingresar el puerto, cerrando aplicacion...");
            System.exit(1);
        }
        try {
            maximoConexiones = 10;
        } catch (Exception e) {
            System.out.println("Error al ingresar el puerto, cerrando aplicacion...");
            System.exit(1);
        }
        servidor = new Servidor(puerto, maximoConexiones);

        System.out.println("DATOS DEL SERVIDOR:\n");
        System.out.println("Nombre del Servidor:\t" + servidor.getNombreServidor());
        System.out.println("IP del Servidor:\t" + servidor.getIPServidor());
        System.out.println("Puerto de Escucha:\t" + servidor.getPuerto());
        System.out.println("\nServidor en espera...");

        while (true) {
            socket = servidor.aceptarConexion();
            if (socket != null)
                new HiloServidor(socket, servidor.getLista()).start();
            else{
            	servidor.pararServidor();
            }
        }
        
    }
    
}
