/*
Clase principal de aplicación en línea de comándos.
Contiene el método main() de la versión en modo texto.
Su ejecución es prácticamente secuencial
*/

//Plataforma de java
/*import java.util.Vector;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintStream;*/

//De JNetPcap
/*import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHandler;
import org.jnetpcap.nio.JBuffer;*/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.nio.ByteBuffer;

import org.jnetpcap.PcapHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.tcpip.*;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.lan.IEEE802dot2;
import org.jnetpcap.protocol.lan.IEEE802dot3;

public class SnifferLC{

    public static void main(String[] argumentos){

        ListaInterfaces listaInterfaces = null;
        Vector<String> nombres = null;
        Pcap interfaz = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sniffer en línea de comandos!");

        try{
            listaInterfaces = new ListaInterfaces();
        }catch(ExceptionSniffer excepcion){
            System.err.println("Error al obtener intrfaces: " + excepcion.getMensaje());
            System.exit(-1);
        }

        System.out.println("Selecciona una interfaz de red:");
        nombres = listaInterfaces.enlistar();
        for(int i=0; i<nombres.size(); i++){
            System.out.println("-> Interfaz " + i + ": " + nombres.elementAt(i));
        }
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        try{
            interfaz = listaInterfaces.abrir(seleccion);
        }catch(ExceptionSniffer excepcion){
            System.err.println("Error al abrir sesión de captura: " + excepcion.getMensaje());
            System.exit(-1);
        }

        //Esto lo hará mas adelante la clase Captura,
        //Sólo que ahora ya tengo que hacer otras cosas ... xD:

        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
			public void nextPacket(PcapPacket packet, String user) {
				System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
				    new Date(packet.getCaptureHeader().timestampInMillis()),
				    packet.getCaptureHeader().caplen(),  // Length actually captured
				    packet.getCaptureHeader().wirelen(), // Original length
				    user                                 // User supplied object
				    );
			}
		};
        int cnt = 10; // Capture packet count
        PrintStream out = System.out; // Our custom object to send into the handler
        interfaz.loop(10, jpacketHandler, "Prueba!");
        interfaz.close();

    }

}
