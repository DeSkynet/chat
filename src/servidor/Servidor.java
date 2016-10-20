package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    private ServerSocket servidor;
    private Socket cliente;
    public static int cantActualClientes;
    private Map <String, ArrayList<Socket>> mapSalas; //Map de Salas
    private int maxClientes;
    private int puerto;
    private String nombreServidor;
    private String IPServidor;
    private String sala;

    public String getNombreServidor() {
        return nombreServidor;
    }

    public String getIPServidor() {
        return IPServidor;
    }
    public int getPuerto() {
        return puerto;
    }
    
    public String getSala(){
    	return sala;
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
        mapSalas=new HashMap<String,ArrayList<Socket>>(); //creo el map.

        try {
            servidor = new ServerSocket(puerto);
        } catch (IOException e) {
            System.out.println("No se puede conectar desde el puerto elegido, cerrando Servidor...");
            System.exit(1);
        }
    }

    public Map<String, ArrayList<Socket>> getLista() {
        return mapSalas;  //le devuelve el array segun la sala.
    }

    public Socket aceptarConexion() {

        cantActualClientes++;

        try {
            cliente = servidor.accept();	//Se Queda esperando clientes.
            if (cantActualClientes > maxClientes) {
                PrintStream ps = new PrintStream(cliente.getOutputStream()); //para enviar algo al cliente.
                ps.println("Servidor Lleno");
                cliente.close(); //cierra el socket.
                return null;
            }
        } catch (Exception e) {
        	System.out.println(cantActualClientes+ " "+ maxClientes );
            System.out.println("Error al aceptar conexiones, Cerrando el Servidor...");
            System.exit(1);
        }
        
        System.out.println(cantActualClientes+" " + maxClientes);
        
       try {
		DataInputStream dato = new DataInputStream(cliente.getInputStream());
			if((sala = dato.readLine()) != null){

				if(mapSalas.containsKey(sala) ==false)
					mapSalas.put(sala, new ArrayList<Socket>() );
				mapSalas.get(sala).add(cliente); 

				System.out.println("La Conexion NRO " + cantActualClientes
		                +" de la sala "+ sala  +" fue aceptada correctamente.");
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}

        return cliente; //devuelvo el socket del cliente
    }
    
    public Socket getCliente() {
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
