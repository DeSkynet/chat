package servidor;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;

public class HiloServidor extends Thread {

    private Socket socket;
    private Collection<Socket> coleccion;
    
    public HiloServidor(Socket socket, Collection<Socket> coleccion) {

        super("ThreadServer");
        this.socket = socket;
        this.coleccion = coleccion;
    }


    @SuppressWarnings("deprecation")
	public void run() {

        DataInputStream data;
        Iterator<Socket> iterador;
        String mensaje = null;

        try {
            do {
                if (mensaje != null) {
                    System.out.println(mensaje);
                    iterador = coleccion.iterator(); //creo un interador de los clientes.

                    while (iterador.hasNext()) {
                        Socket cliente = iterador.next(); //le pido un cliente de la coleccion.
                        try {

                            // si el socket extraido es distinto al socket del
                            // hilo
                            // se enviara el msg a todos los usuarios de la
                            // coleccion menos el que envio dicho msg.
                            if (!cliente.equals(socket)) {
                                PrintStream ps = new PrintStream(
                                        cliente.getOutputStream());
                                ps.println(mensaje);// envia el mensaje al
                                                // correspondiente socket.
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // indico que el flujo de informacion provenga del usuario de
                // este hilo.
                data = new DataInputStream(socket.getInputStream());

            } while ((mensaje = data.readLine()) != null);

            Servidor.cantActualClientes--;
            coleccion.remove(socket);
            System.out.println("Un cliente se ha desconectado.");
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("La conexion ha finalizado.");
        }
    }
}
