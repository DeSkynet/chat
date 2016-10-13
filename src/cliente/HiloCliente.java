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
        	datos = new DataInputStream(socket.getInputStream());
        	mensaje = datos.readLine();
        	if (mensaje != null)
    			System.out.println(mensaje);
        	while ((mensaje = datos.readLine()) != null){
        		System.out.println(mensaje);
        		datos = new DataInputStream(socket.getInputStream());
        	}
        } catch (IOException e) {
           // cerrarSocket();
        }
    }
}
