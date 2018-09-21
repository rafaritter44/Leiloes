package servidor;

import java.net.ServerSocket;

public class AppServidor {
    
	public static final int porta = 8888;
	
	public static void main(String[] args) throws Exception {

		Gerenciador g = new Gerenciador();
		
		System.out.println("--- Inicializando servidor na porta " + porta + " ---");
		ServerSocket ss = new ServerSocket(porta);
	    System.out.println("--- Servidor inicializado com sucesso ---");
		
        try {
            while (true) {
                new Conexao(ss.accept(), g).start();
            }
        } finally {
            ss.close();
        }
    }
}