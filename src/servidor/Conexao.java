package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Mensagem;
import model.Pessoa;
import model.Produto;

public class Conexao extends Thread {
    private Socket socket;
    private String username;
    public Gerenciador g;

    public Conexao(Socket socket, Gerenciador g) {
        this.socket = socket;
        this.g = g;
        username = "";
//        System.out.println("Nova conexao estabelecida em " + socket);
    }

    public void run() {
        try {
    		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        	

            while (true) {
            	os.reset();
                Mensagem im = (Mensagem) is.readObject();
//                System.out.println(im);
                
                if (im.operacao.equals("ValidarUsuario")) {
                	System.out.println("Validando Usuario: " + im.username);
                	Mensagem om = new Mensagem("ValidarUsuario", username, "");
            		username = im.username;
                	
                	if (g.usernameExiste(im.username)) {
                		om.args = "Existe"; 
                	} else {
                		om.args = "NaoExiste";
                	}

//                	System.out.println("Respondendo: " + om);
                	os.writeObject(om);
                	continue;
                }
                
                else if (im.operacao.equals("CadastrarUsuario")) {
                	Pessoa p = (Pessoa) is.readObject();
                	g.addPessoa(p);
                }
                
                else if (im.operacao.equals("Encerrar")) {
                    System.out.println("Encerrando conexao com usuario: " + username);
                	break;
                }
                
                switch (im.operacao) {
                case "ConsultarConta":
                	Pessoa om = g.pessoas.get(username);
                	System.out.println("Respondendo Objeto: " + om);
                	os.writeObject(om);
                	break;
                	
                case "AdicionarProduto":
                	Produto produtoAdicionado = (Produto) is.readObject();
//                	System.out.println("Adicionando produto: " + produtoAdicionado);
                	g.pessoas.get(username).addProduto(produtoAdicionado);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na conexao: " + e);
        } finally {
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.out.println("Conexao Encerrada");
        }
    }
}