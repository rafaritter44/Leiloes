package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AppCliente {

	public static final int porta = 5555;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		System.out.println("--- Pontificia Universidade Catolica do Rio Grande do Sul ---"
				+ "\n--- Bacharelado em Engenharia de Software ---"
				+ "\n--- Disciplina de Projeto e Arquitetura de Software ---"
				+ "\n--- Prof. Ana Paula Terra Bacelo ---"
				+ "\n--- Gabriel Ferreira Kurtz e Rafael Barni Ritter ---"
				+ "\n\n----------------------------------------"
				+ "\n--- PROGRAMA DE SIMULACAO DE LEILOES ---"
				+ "\n----------------------------------------");

		
    	
    	System.out.println("--- Conectando ao Servidor ---");
		Socket s = new Socket("localhost", porta);
		System.out.println("--- Conexão Estabelecida com Sucesso ---");

		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());
		
		InterfaceTexto inter = new InterfaceTexto(os, is);
//		inter.menuPrincipal();

		s.close();
    }
}
