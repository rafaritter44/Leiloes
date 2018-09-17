package model;

public class Oferta {
	
	private Pessoa comprador;
	private double valor;
	
	public Oferta(Pessoa comprador, double valor) {
		this.comprador = comprador;
		this.valor = valor;
	}
	
	public Pessoa getComprador() { return comprador; }
	public double getValor() { return valor; }

}
