package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import model.CartaoDeCredito;
import model.Mensagem;
import model.Pessoa;
import model.Produto;

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
			System.out.println("Pessoa Enviada: " + p);
			os.writeObject(p);
	    } else {
	    	System.out.println("\n--- Usuário Encontrado. Efetuando login ---");
	    }
	    
	}
	
	public void menuPrincipal() throws ClassNotFoundException, IOException {
		os.reset();
		String opcaoString = "0";
		int opcaoInt = Integer.parseInt(opcaoString);
		
		while(opcaoInt != 9){
			System.out.println("\n----------------------"
					+ "\n--- MENU PRINCIPAL ---"
					+ "\n----------------------"
					+ "\n--- Selecione uma opção: "
					+ "\n1) Consultar/Editar Conta"
					+ "\n2) Adicionar Produto"
					+ "\n3) Vender Produto"
					+ "\n4) Acompanhar Vendas"
					+ "\n5) Comprar Produto"
					+ "\n9) Encerrar Programa");

			System.out.print("> ");
			opcaoString = in.nextLine();
			
			while(!StringUtils.isNumeric(opcaoString)){
				System.out.println("--- ERRO: Insira somente o numero da opcao ---");
				System.out.print("> ");
				opcaoString = in.nextLine();
			}
			
			opcaoInt = Integer.parseInt(opcaoString);
			
			if(opcaoInt == 1){
				consultarConta();
			}
			
			else if(opcaoInt == 2){
				adicionarProduto();
			}
		
			else if(opcaoInt == 3){
				venderProduto();
			}
			
			else if(opcaoInt == 4){
				acompanharVendas();
			}
			
			else if(opcaoInt == 5){
				comprarProduto();
			}
		}
		
		
		Mensagem fim = new Mensagem("Encerrar", username, "");
		os.writeObject(fim);
		System.out.println("\n-----------------------"
						 + "\n--- Fim do Programa ---"
						 + "\n-----------------------");
	}

	public void consultarConta() throws IOException, ClassNotFoundException {
		Mensagem om = new Mensagem("ConsultarConta", username, "");
		os.writeObject(om);
		Pessoa im = (Pessoa) is.readObject();
//		System.out.println("Recebido: " + im);
		
		System.out.println("\n--- CONTA DO USUARIO ---"
				+ "\n--------------------------------------------"
				+ "\n--- Username: " + username
				+ "\n--- Nome Completo: " + im.getNome()
				+ "\n--- Saldo: " + im.getSaldo()
				+ "\n--------------------------------------------");
		
		System.out.println("\n--- CARTAO DE CREDITO ---"
				+ "\n--------------------------------------------"
				+ "\n--- Numero: " + im.cartaoDeCredito.numero
				+ "\n--- Bandeira: " + im.cartaoDeCredito.bandeira
				+ "\n--------------------------------------------");
		
		System.out.println("\n--- PRODUTOS ---"
				+ "\n--------------------------------------------");
		
		if(im.produtos.isEmpty()) {
			System.out.println("Usuario nao possui nenhum produto");
		} else {
			for(Produto p : im.produtos) {
				if(p.estaAVenda()) {
					System.out.print("[VENDA]\t");
				}
				System.out.println(p.getNome());
			}
		}
		
		System.out.println("--------------------------------------------");
	}
	
	public void adicionarProduto() throws IOException {
		System.out.println("\n--- ADICIONAR PRODUTO ---"
				+ "\n--------------------------------------------"
				+ "\nNome do Produto: ");
		String nome = in.nextLine();

		Produto p = new Produto(nome);
		p.setIdDono(username);

		Mensagem om = new Mensagem("AdicionarProduto", username, "");
		os.writeObject(om);
		os.writeObject(p);
	}
	
	public void venderProduto() throws IOException, ClassNotFoundException {
		Mensagem om = new Mensagem("VenderProduto", username, "");
		os.writeObject(om);
		Pessoa vendedor = (Pessoa) is.readObject();

		if(vendedor.produtos.isEmpty()) {
			System.out.println("xxx ERRO: Usuario nao possui nenhum produto xxx");
			return;
		} else {
			Collections.sort(vendedor.produtos);
		}

		HashMap<Integer, Produto> mapaProdutos = new HashMap<Integer, Produto>();
		System.out.println("\n--- PRODUTOS ---"
				+ "\n--------------------------------------------");

		for(Produto p : vendedor.produtos) {
			if(p.estaAVenda()) {
				System.out.print("[VENDA]");
			} else {
				System.out.print(p.getId());
			}
			System.out.println("\t" + p.getNome());
			mapaProdutos.put(p.getId(), p);
		}
		
		System.out.println("--------------------------------------------\n"
				+ "Digite o ID do produto para vender ou 0 para retornar:");
		
		System.out.print("> ");
		String opcaoString = in.nextLine();
		
		while(!StringUtils.isNumeric(opcaoString)){
			System.out.println("--- ERRO: Insira somente o numero do produto ---");
			System.out.print("> ");
			opcaoString = in.nextLine();
		}
		
		om = new Mensagem("IdVenda", username, opcaoString);
		os.writeObject(om);
		
		Mensagem im = (Mensagem) is.readObject();
		
		int opcaoInt = Integer.parseInt(opcaoString);
		if (opcaoInt == 0) {
			System.out.println("\n--- Cancelado - Retornando ao Menu Principal ---");
			return;
		}
		
		if (im.args.equals("true")) {
			System.out.println("--- Leilao de Venda criado com sucesso ---");
		} else if (im.args.equals("ProdutoAVenda")){
			System.out.println("--- ERRO: O produto ja esta a venda ---");
		} else if (im.args.equals("ProdutoInexistente")){
			System.out.println("--- ERRO: Codigo de Produto Invalido ---");
		}
	}
	
	public void acompanharVendas() {
		
	}
	
	public void comprarProduto() {
		
	}
	
	public Pessoa cadastrarUsuario() {
		System.out.println("\n--- Usuario não encontrado. Favor realizar cadastro ---\n"
				+ "Nome Completo: ");
		String nome = in.nextLine();
		CartaoDeCredito cartao = new CartaoDeCredito();

		return new Pessoa(username, nome, 0.0, cartao);
	}
}
