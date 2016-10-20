package cliente;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloCliente extends Thread {
    private Socket socket;

    public HiloCliente(Socket socket) {
        super("ThreadCliente");
        this.socket = socket;
    }


	@SuppressWarnings("deprecation")
	public void run() {
        DataInputStream datos;
        String mensaje = null;

        try {
        	datos = new DataInputStream(socket.getInputStream()); //le digo que tiene que leer del Socket
        	mensaje = datos.readLine(); //paso el dato a un string
        	if (mensaje != null)
    			System.out.println(mensaje); //lo muestro en mi pantalla.
        	while ((mensaje = datos.readLine()) != null){
        		System.out.println(mensaje);
        		datos = new DataInputStream(socket.getInputStream()); //Se queda escuchando al socket..
        	}
        } catch (IOException e) {
           cerrarSocket();  //si no puede o deja de comunicarse, se cierra el socket.
        }
    }
	
	public void cerrarSocket(){
    	try {
            socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
