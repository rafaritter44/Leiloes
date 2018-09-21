package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Scanner;

import model.Mensagem;

public class InterfaceTexto {
	public String username;
	public Scanner in;
	public Path textoOriginal;
	public ObjectOutputStream os;
	public ObjectInputStream is;
	
	public InterfaceTexto(ObjectOutputStream os, ObjectInputStream is) throws IOException, ClassNotFoundException {
		in = new Scanner(System.in);
		this.os = os;
		this.is = is;
		inicializarInterface();
	}
	
	public void inicializarInterface() throws IOException, ClassNotFoundException {
		System.out.println("\nInforme o Username: ");
	    this.username = in.nextLine();
	    
	    Mensagem om = new Mensagem("ValidaUsuario", username, "");
	    os.writeObject(om);
	    Mensagem im = (Mensagem) is.readObject();
	    
	    System.out.println(im);
	    
	}
}
