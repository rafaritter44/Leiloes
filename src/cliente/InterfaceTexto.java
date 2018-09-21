package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Scanner;

import model.CartaoDeCredito;
import model.Mensagem;
import model.Pessoa;

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
	    
	    Mensagem om = new Mensagem("ValidarUsuario", username, "");
	    os.writeObject(om);
	    
	    Mensagem im = (Mensagem) is.readObject();
	    if (im.args.equals("NaoExiste")) {
	    	Pessoa p = cadastrarUsuario();
			om = new Mensagem("CadastrarUsuario", username, "");
			os.writeObject(om);
			os.writeObject(p);
	    } else {
	    	System.out.println("\n--- Usuário Encontrado. Efetuando login ---");
	    }
	    
	}
	
	public Pessoa cadastrarUsuario() {
		System.out.println("\n--- Usuario não encontrado. Favor realizar cadastro ---\n"
				+ "Nome Completo: ");
		String nome = in.nextLine();
		CartaoDeCredito cartao = new CartaoDeCredito();

		return new Pessoa(username, nome, 0.0, cartao);
	}
}
