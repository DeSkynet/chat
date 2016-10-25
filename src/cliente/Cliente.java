package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private Socket cliente;
    private String nombre = null;
    private String host;
    private String sala;
    private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public Cliente() {
        try {
            leerArchivoConfig();
            this.cliente = new Socket(host, puerto);
            System.out.println("Conexión Exitosa.");
        } catch (IOException e) {
        	System.out.println(e.getLocalizedMessage());
            System.out.println("No se pudo conectar con el servidor, cerrando el  programa...");
            System.exit(1);
        }
    }
    
    private void leerArchivoConfig() {
		Scanner entrada = null;
    	try {
			entrada = new Scanner(new File("archivos/conexion.config"));
			
			if(entrada.hasNextLine()) {
				this.host = entrada.nextLine().substring(3);
				this.puerto = Integer.parseInt(entrada.nextLine().substring(7));
			}
			
		} catch (FileNotFoundException e) {
				System.err.println(e.getLocalizedMessage());
		} finally {
			entrada.close();
		}
    	entrada.close();
	}

    public Socket getSocket() {
        return cliente;
    }
    
    public String getSala() {
        return sala;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setSala(String sala) {
        this.sala = sala;
    }

    public void enviarMensaje() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  //Se queda esperando que escribas algo en consola..
            //Se lee desde el host del usuario y dirige el flujo o información al server
            PrintStream ps = new PrintStream(cliente.getOutputStream()); //le digo a donde lo tiene que mandar.. cliente es el socket.

            String mensaje;
            while ((mensaje = br.readLine()) != null) {

                if (mensaje.equals(":salir")) {
                    System.out.println("Cerrando Aplicacion...");
                    cerrarCliente();
                } else if (!mensaje.equals("")) { // ESTO ES POR SI SE TOCA ENTER SIN INGRESAR NADA
                    ps.println(nombre + " dijo:  " + mensaje); //MANDO el mensaje por el socket.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cerrarCliente() {
        try {
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void eligeSala() {
		 try {
			PrintStream psSalida = new PrintStream(cliente.getOutputStream()); //abro el canal
			psSalida.println(sala);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	
}
