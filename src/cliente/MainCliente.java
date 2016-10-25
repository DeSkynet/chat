package cliente;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainCliente {

    public static void main(String args[]) throws IOException {
    	Scanner scCliente;

        System.out.println("CLIENTE DE CHAT POR CONSOLA:\n");

        Cliente cliente = new Cliente();
        
        System.out.println("Ingrese el nombre de la sala: ");
        scCliente = new Scanner(new InputStreamReader(System.in));
        cliente.setSala(scCliente.nextLine());
       

        System.out.println("Ingrese su nombre de usuario: ");
        scCliente = new Scanner(new InputStreamReader(System.in));
        cliente.setNombre(scCliente.nextLine());
        
        System.out.println("\n(Ingrese :salir para cerrar sesion)\n");
        
        
        HiloCliente threadCliente = new HiloCliente(cliente.getSocket()); //creo el hilo Cliente y le mando el socket. Espera un mensaje.
        threadCliente.start(); //inicializo el hilo, ejecuta el run.
        cliente.eligeSala();  //le dice al servidor en que sala quiere estar.
        
        cliente.enviarMensaje();
        cliente.cerrarCliente();
    }
}
