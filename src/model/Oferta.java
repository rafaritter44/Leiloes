package model;

import java.io.Serializable;

public class Oferta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Pessoa comprador;
	private double valor;
	
	public Oferta(Pessoa comprador, double valor) {
		this.comprador = comprador;
		this.valor = valor;
	}
	
	public Pessoa getComprador() { return comprador; }
	public double getValor() { return valor; }

	@Override
	public String toString() {
		return "Oferta [comprador=" + comprador + ", valor=" + valor + "]";
	}

}
