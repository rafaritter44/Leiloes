package model;

import java.io.Serializable;

public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String idDono;
	private boolean emLeilao;
	private static int contador = 1;
	
	public Produto(String nome) {
		setNome(nome);
		this.id = contador;
		contador++;
	}

	public int getId() { return id; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getIdDono() { return idDono; }
	public void setIdDono(String idDono) { this.idDono = idDono; }
	public boolean estaAVenda() { return emLeilao; }
	public void setEmLeilao(boolean emLeilao) { this.emLeilao = emLeilao; }

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", idDono=" + idDono + ", emLeilao=" + emLeilao + "]";
	}

}
