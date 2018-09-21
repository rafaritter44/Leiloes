package model;

import java.io.Serializable;

public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String idDono;
	private boolean emLeilao;
	
	public Produto(int id, String nome) {
		setId(id);
		setNome(nome);
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getIdDono() { return idDono; }
	public void setIdDono(String idDono) { this.idDono = idDono; }
	public boolean estaAVenda() { return emLeilao; }
	public void setEmLeilao(boolean emLeilao) { this.emLeilao = emLeilao; }

}
