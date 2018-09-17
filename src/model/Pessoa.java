package model;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	
	private int id;
	private String nome;
	private List<Produto> produtos;
	private double saldo;
	
	public Pessoa(int id, String nome, double saldo) {
		this.id = id;
		this.nome = nome;
		produtos = new ArrayList<>();
		this.saldo = saldo;
	}
	
	public void addProduto(Produto produto) {
		produtos.add(produto);
		produto.setIdDono(id);
	}
	
	public boolean removeProduto(Produto produto) {
		return produtos.remove(produto);
	}
	
	public int getId() { return id; }
	public String getNome() { return nome; }
	public double getSaldo() { return saldo; }

}
