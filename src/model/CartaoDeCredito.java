package model;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class CartaoDeCredito implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String numero;
	public String bandeira;
	public String cvv;
	
	public CartaoDeCredito() {
		geraAleatorio();
	}
	
	public CartaoDeCredito(String numero, String bandeira, String cvv) {
		this.numero = numero;
		this.bandeira = bandeira;
		this.cvv = cvv;
	}
	
	public boolean isValido() {
		return numero != null && bandeira != null && cvv != null &&
				numero.length() == 16 && bandeira.equalsIgnoreCase("visa")
				&& cvv.length() == 3;
	}
	
	public void geraAleatorio() {
		long nCartao = 4000000000000000L;
		nCartao += ThreadLocalRandom.current().nextLong(3000000000000000L);
		this.numero = Long.toString(nCartao);
		if (nCartao % 2 == 0) {
			this.bandeira = "Visa";
		} else {
			this.bandeira = "Mastercard";
		}
		int nCvv = 100;
		nCvv += ThreadLocalRandom.current().nextInt(899);
		this.cvv = Integer.toString(nCvv);
	}

	@Override
	public String toString() {
		return "CartaoDeCredito [numero=" + numero + ", bandeira=" + bandeira + ", cvv=" + cvv + "]";
	}
}
