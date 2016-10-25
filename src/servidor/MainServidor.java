package servidor;

import java.net.Socket;

public class MainServidor {
    public static void main(String[] args) {
        Socket socket = null;

        Servidor servidor = null;
        int maximoConexiones = 10;

        System.out.println("	SERVIDOR DE CHAT POR CONSOLA	\n");
        servidor = new Servidor(maximoConexiones);

        System.out.println("********	DATOS DEL SERVIDOR	********\n");
        System.out.println("IP del Servidor:\t" + servidor.getIPServidor());
        System.out.println("Puerto de Escucha:\t" + servidor.getPuerto());
        System.out.println("\nServidor en espera...");

        while (true) {
            socket = servidor.aceptarConexion();

            if (socket != null) //pregunta esto por si el servidor esta lleno.
                new HiloServidor(socket, servidor.getLista(), servidor.getSala()) .start(); //Crea el hilo por cada conexion y lo pone a andar
            else{
            	servidor.pararServidor(); //me parece que para el servidor si hay mas personas de las que puede conectadas.
            }
        }
        
    }
    
}
