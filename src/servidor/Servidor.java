package servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

public class Servidor {

    private ServerSocket servidor;
    private Socket cliente;
    public static int cantActualClientes;
    private Collection<Socket> coleccionSockets;
    private int maxClientes;
    private int puerto;
    private String nombreServidor;
    private String IPServidor;

    public String getNombreServidor() {
        return nombreServidor;
    }

    public String getIPServidor() {
        return IPServidor;
    }
    public int getPuerto() {
        return puerto;
    }

    public Servidor(int puerto, int maxConexiones) {

        try {
            nombreServidor = InetAddress.getLocalHost().getHostName().toString();  //getLocalHost(): regresa la direccion ip de la maquina donde se esta ejecutando el programa.
            IPServidor = InetAddress.getLocalHost().getHostAddress().toString();	//getByName(): regresa la direccion ip de la maquina que se especifica como parametro.
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

        this.puerto = puerto;
        maxClientes = maxConexiones;

        cantActualClientes = 0;
        coleccionSockets = new ArrayList<Socket>();

        try {
            servidor = new ServerSocket(puerto);
        } catch (IOException e) {
            System.out.println("No se puede conectar desde el puerto elegido, cerrando Servidor...");
            System.exit(1);
        }
    }

    public Collection<Socket> getLista() {
        return coleccionSockets;
    }

    public Socket aceptarConexion() {

        cantActualClientes++;

        try {
            cliente = servidor.accept();
            if (cantActualClientes > maxClientes) {
                PrintStream ps = new PrintStream(cliente.getOutputStream());
                ps.println("Servidor Lleno");
                cliente.close();
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al aceptar conexiones, Cerrando el Servidor...");
            System.exit(1);
        }
        System.out.println("La Conexion NRO " + cantActualClientes
                + " fue aceptada correctamente.");
        coleccionSockets.add(cliente);
        return cliente;
    }
    public void pararServidor() {
        try {
            servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
