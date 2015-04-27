import javax.comm.*;
import java.io.*;
import java.util.*;

import com.sun.comm.Win32Driver;

/*
Cette classe permet de tester les ports séries disponibles
en les listant puis en créant un stream d'entrée et de sortie
*/
public class TesteJavaComm extends Thread implements SerialPortEventListener
{
	CommPortIdentifier portId;
	Enumeration portList;
	BufferedReader in;
	PrintWriter out;
	SerialPort serialPort;
	Vector<String> listData;
	private boolean running;
	
	/*
	Constructeur de la classe TesteJavaComm qui prend en paramètre le port série à utiliser.
	Dans un premier temps on liste les ports disponibles sur le pc
	*/
	public TesteJavaComm ()
	{
		listData =  new Vector<String>();
		System.out.println("listage des ports série disponibles:");
		listePortsDispo();
		
	}//fin constructeur
	
	public void listePortsDispo()
	{
		System.out.println("recherche...");
		//initialisation du driver
		Win32Driver w32Driver= new Win32Driver();
		w32Driver.initialize();
		//récupération de l'énumération
		portList=CommPortIdentifier.getPortIdentifiers();
		//affichage des noms des ports
		CommPortIdentifier portId;
		int nbr=0;
		while (portList.hasMoreElements()){
			portId=(CommPortIdentifier)portList.nextElement();
			listData.add(portId.getName());
			System.out.println(listData.get(nbr));
			nbr++;
		}
	} //fin de la methode listePortsDispo()
	
	
	public void ConnexionPort (String com)
	{
		
	try
	{
		//ouverture du port série:
		portId=CommPortIdentifier.getPortIdentifier(com);
		serialPort=(SerialPort)portId.open("Mon_Appli",2000);
		
		//on sélectionne tous les paramètres de la connexion série:
		serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,
		SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
		System.out.println("Ouverture du port "+com);
		
		//pour lire et écrire avec des streams:
		in=new BufferedReader(
		new InputStreamReader(serialPort.getInputStream()));
		out=new PrintWriter(serialPort.getOutputStream());
		
		
		//ajout du listener
		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
		}
		
		//Teste de lecture/ecriture sur le port série
		
		String originalText="une chaine de caractere";
		char[] car =  originalText.toCharArray();
		out.write(car, 0, originalText.length());//.write(outputArray, 0 , outputArray.length ); 
		
		int b = in.read();
		in.close();
		out.close();
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	}
	
	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
		//fermeture du flux et port
		try {
			in.close();
		} catch (IOException e) {
		}
		serialPort.close();
	}
	
	/**
	 * Méthode de gestion des événements.
	 */
	public void serialEvent(SerialPortEvent event) {
		//gestion des événements sur le port :
		//on ne fait rien sauf quand les données sont disponibles
		switch (event.getEventType()) {
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				break;
			case SerialPortEvent.DATA_AVAILABLE :
				String codeGPS = new String(); 
				try {
					//lecture du buffer et affichage
					codeGPS = (String) in.readLine();
					System.out.println(codeGPS);
				} catch (IOException e) {
				}
				break;
		}
	}
	
	/*
	Methode main qui permet de tester notre classe de teste en ouvrant une connexion sur le port COM1.
	*/
	public static void main(String args[])
	{
		AffichageJavaComm Fenetre = new AffichageJavaComm();
	}//fin du main
	
}//fin de la classe