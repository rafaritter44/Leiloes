package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String nome;
	public List<Produto> produtos;
	private double saldo;
	public CartaoDeCredito cartaoDeCredito;
	
	public Pessoa(String username, String nome, double saldo, CartaoDeCredito cartaoDeCredito) {
		this.username = username;
		this.nome = nome;
		produtos = new ArrayList<>();
		this.saldo = saldo;
		this.cartaoDeCredito = cartaoDeCredito;
	}
	
	public void addProduto(Produto produto) {
		produtos.add(produto);
		produto.setIdDono(username);
	}
	
	public boolean removeProduto(Produto produto) {
		return produtos.remove(produto);
	}
	
	public String getUsername() { return username; }
	public String getNome() { return nome; }
	public double getSaldo() { return saldo; }
	
	public void recebe(double valor) {
		saldo += valor;
	}
	
	public boolean validaCartao() {
		return cartaoDeCredito != null
				&& cartaoDeCredito.isValido();
		}

	@Override
	public String toString() {
		return "Pessoa [username=" + username + ", nome=" + nome + ", produtos=" + produtos + ", saldo=" + saldo
				+ ", cartaoDeCredito=" + cartaoDeCredito + "]";
	}

}
