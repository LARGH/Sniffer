//Árbol de excepciones

/*
No estoy seguro de si implementar nuestras propias excepciones sea
una muy buena idea. De momento solo lo hago para no usar el esquema
de errores de JNetPcap, que de manera parecida a C regresa números de
estátus para muchas cosas. (... también lo hago para experimentar
con las excepciones ...)

Por ejemplo, se lanza cuando Pcap no puede encontrar ninguna
interfaz de red. En la versión en modo texto esta excepción no tiene mucho
sentido, pues el no encontrar una interfaz es un error fatal
que terminaría con la ejecución. Sin embargo, la hago pensando
en la versión con GUI: al no encontrar una interfaz el usuario
aún puede realizar más acciónes (incluso cambiar la configuración
de su sistema y volver a intentar la búsqueda de interfaces).

También se lanza cuando ocurre un error al intentar abrir una
nueva sesión de captura.
*/

public class ExceptionSniffer extends Throwable{

    private String mensaje;

    public ExceptionSniffer(String mensaje){
        super();
        this.mensaje = mensaje;
    }

    public String getMensaje(){
        return mensaje;
    }
}
