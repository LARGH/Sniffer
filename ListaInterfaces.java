/*
Clase que sirve de enlace con las interfaces de red.
Esta pensada para usarse tanto en la versión de consola
como en la de interfaz gráfica.

ListaInterfaces() --> Inicializa la lista de interfaces.
                      Lanza ExceptionSinInterfaces si hay
                      algún error al obtener las interfaces.

abrir() ------------> Recibe el número de interfaz en el que
                      se quiera capturar.
                      Regresa un objeto Pcap asociado a la
                      interfaz seleccionada.

enlistar() ---------> Regresa un vector de strings con la
                      lista de nombres de interfaces.
*/

//Plataforma de java
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

//De JNetPcap
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class ListaInterfaces{

    private List<PcapIf> interfaces;
    private StringBuilder errores;                                 //Muy al estilo de errno, en C
    private PcapIf interfaz;                                       //Interfaz seleccionada

    public ListaInterfaces() throws ExceptionSniffer{

        errores = new StringBuilder();
        interfaces = new ArrayList<PcapIf>();
        int estatus = Pcap.findAllDevs(interfaces, errores);       //Esto llena la lista
        if(estatus != Pcap.OK){                                    //(igual a como se haría en C).
            throw new ExceptionSniffer(errores.toString());
        }

    }

    public Pcap abrir(int numeroDeInterfaz) throws ExceptionSniffer{

        interfaz = interfaces.get(numeroDeInterfaz);
        int longitud = 2048;                                       //Máximo # bytes por paquete
        int promiscuo = Pcap.MODE_PROMISCUOUS;                     //Modo de apertura (promiscuo o no promiscuo)
        int tiempo = 60 * 1000;                                    //Tiempo de captura
        Pcap regreso = Pcap.openLive(interfaz.getName(),
                                     longitud,
                                     promiscuo,
                                     tiempo,
                                     errores);
        if(errores != null){
            throw new ExceptionSniffer(errores.toString());
        }
        return regreso;

    }

    public Vector<String> enlistar(){

        Vector<String> lista = new Vector<String>();
        for(int i=0; i<interfaces.size(); i++){
            lista.addElement(interfaces.get(i).getName());
        }
        return lista;

    }

}
