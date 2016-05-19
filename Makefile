SnifferLC.class: SnifferLC.java
	javac -cp .:bibliotecas/jnetpcap.jar SnifferLC.java

ListaInterfaces.class:
	javac -cp .:bibliotecas/jnetpcap.jar ListaInterfaces.java

ExceptionSniffer.class:
	javac -cp .:bibliotecas/jnetpcap.jar ExceptionSniffer.java

run: ExceptionSniffer.class SnifferLC.class ListaInterfaces.class
	java -cp .:bibliotecas/jnetpcap.jar SnifferLC

clean:
	rm *.class
