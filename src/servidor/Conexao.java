package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import model.Leilao;
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
//                	System.out.println("Respondendo Objeto: " + om);
                	os.writeObject(om);
                	break;
                	
                case "AdicionarProduto":
                	Produto produtoAdicionado = (Produto) is.readObject();
//                	System.out.println("Adicionando produto: " + produtoAdicionado);
                	g.pessoas.get(username).addProduto(produtoAdicionado);
                	break;
                
                case "VenderProduto":
               	   	Pessoa vendedor = g.pessoas.get(username);
                	os.writeObject(vendedor);
                	
            		HashMap<Integer, Produto> mapaProdutos = new HashMap<Integer, Produto>();
            		for(Produto p : vendedor.produtos) {
            			mapaProdutos.put(p.getId(), p);
            		}

            		Mensagem msgVenda = (Mensagem) is.readObject();
            		int idVenda = Integer.parseInt(msgVenda.args);
            		
            		if (mapaProdutos.get(idVenda) == null) {
            			msgVenda = new Mensagem("resultadoVenda", username, "ProdutoInexistente");
            			os.writeObject(msgVenda);
            			break;
            		}
            		
            		Produto produtoVendido = mapaProdutos.get(idVenda);
            		if (produtoVendido.estaAVenda()) {
            			msgVenda = new Mensagem("resultadoVenda", username, "ProdutoAVenda");
            			os.writeObject(msgVenda);
            			break;
            		}
            		
            		Leilao novoLeilao = new Leilao(idVenda, vendedor, produtoVendido);
            		g.addLeilao(novoLeilao);
            		msgVenda = new Mensagem("resultadoVenda", username, "true");
            		os.writeObject(msgVenda);
                	break;
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