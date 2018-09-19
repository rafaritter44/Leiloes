package model;

public class CartaoDeCredito {
	
	private String numero;
	private String bandeira;
	private String cvv;
	
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

}
