package servidor;

import java.net.ServerSocket;

public class AppServidor {
    
	public static final int porta = 4444;
	
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

//    private static class Conexao extends Thread {
//        private Socket socket;
//        private String username;
//
//        public Conexao(Socket socket) {
//            this.socket = socket;
//            username = "";
//            System.out.println("Nova conex�o estabelecida em " + socket);
//        }
//
//        /**
//         * Services this thread's client by first sending the
//         * client a welcome message then repeatedly reading strings
//         * and sending back the capitalized version of the string.
//         */
//        public void run() {
//            try {
//
//                // Decorate the streams so we can send characters
//                // and not just bytes.  Ensure output is flushed
//                // after every newline.
//        		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
//        		ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
//            	
//
//                // Get messages from the client, line by line; return them
//                // capitalized
//                while (true) {
//                    Mensagem im = (Mensagem) is.readObject();
//                    System.out.println(im);
//
////                    if (input == null || input.equals(".")) {
////                        break;
////                    }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("Erro na conex�o: " + e);
//            } finally {
//            	try {
//					socket.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//                System.out.println("Conexao Encerrada");
//            }
//        }
//    }
}

